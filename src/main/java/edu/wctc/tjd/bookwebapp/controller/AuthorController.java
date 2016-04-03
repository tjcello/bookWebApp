/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.tjd.bookwebapp.controller;

import static com.sun.corba.se.spi.presentation.rmi.StubAdapter.request;
import edu.wctc.tjd.bookwebapp.ejb.AuthorFacade;
import edu.wctc.tjd.bookwebapp.model.Author;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

/**
 *
 * @author Thomas
 */
@WebServlet(name = "AuthorController", urlPatterns = {"/AuthorController"})
public class AuthorController extends HttpServlet {

    private static final String PRODUCTS = "Authors.jsp";
    private static final String PRODUCT_ADD = "add.jsp";
    private static final String PRODUCT_EDIT = "edit.jsp";

    private String driverClass;
    private String url;
    private String userName;
    private String password;
    private String dbJndiName;

    @Inject
    private AuthorFacade as;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException, Exception {

        HttpSession session = request.getSession();
        ServletContext ctx = request.getServletContext();

        response.setContentType("text/html;charset=UTF-8");

        String dest = "";
        String taskType = request.getParameter("taskType");

        configDbConnection();

        try {
            if (taskType.equals("viewAuthor")) {
                System.out.println("hi");
                request.setAttribute("authors", as.getAuthorList());
                dest = PRODUCTS;
            } else if (taskType.equals("deleteAuthor")) {
                String authorId = (String) request.getParameter("id");
                as.deleteAuthorById(authorId);
                this.refreshList(request);
                dest = PRODUCTS;
            } else if (taskType.equals("edit")) {
                String authorId = (String) request.getParameter("id");
                Author author = as.getAuthorById(authorId);
                request.setAttribute("author", author);
                dest = PRODUCT_EDIT;
            } else if (taskType.equals("add")) {
                dest = PRODUCT_ADD;
            } else if (taskType.equals("save")) {
                String authorName = request.getParameter("authorName");
                String authorId = request.getParameter("authorId");
                as.saveAuthor(authorId, authorName);
                this.refreshList(request);
                dest = PRODUCTS;
            } else if (taskType.equals("new")) {
                String authorName = request.getParameter("authorName");

                if (authorName != null) {
                    as.saveAuthor(null, authorName);

                }
                this.refreshList(request);
                dest = PRODUCTS;
            } else if (taskType.equals("cancel")) {
                this.refreshList(request);
                dest = PRODUCTS;
            }
        } catch (Exception e) {

        }
        RequestDispatcher view = request.getRequestDispatcher(response.encodeURL(dest));
        view.forward(request, response);

    }

    private void refreshList(HttpServletRequest request) throws ClassNotFoundException, SQLException {
        List<Author> authors = as.getAuthorList();
        request.setAttribute("authors", authors);
    }

    private void configDbConnection() throws NamingException, Exception {
         if(dbJndiName == null) {
            as.getDao().initDao(driverClass, url, userName, password);   
        } else {
            /*
             Lookup the JNDI name of the Glassfish connection pool
             and then use it to create a DataSource object.
             */
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup(dbJndiName);
            as.getDao().initDao(ds);
        }
    }

    @Override
    public void init() throws ServletException {
        // Get init params from web.xml
//        driverClass = getServletContext().getInitParameter("db.driver.class");
//        url = getServletContext().getInitParameter("db.url");
//        userName = getServletContext().getInitParameter("db.username");
//        password = getServletContext().getInitParameter("db.password");
        dbJndiName = getServletContext().getInitParameter("db.jndi.name");
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(AuthorController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(AuthorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(AuthorController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(AuthorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
