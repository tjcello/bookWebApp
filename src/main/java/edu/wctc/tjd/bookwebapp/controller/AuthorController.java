/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.tjd.bookwebapp.controller;

import static com.sun.corba.se.spi.presentation.rmi.StubAdapter.request;
import edu.wctc.tjd.bookwebapp.model.Author;
import edu.wctc.tjd.bookwebapp.model.AuthorService;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Thomas
 */
@WebServlet(name = "AuthorController", urlPatterns = {"/AuthorController"})
public class AuthorController extends HttpServlet {
    
    
    
    private static final String AUTHORS = "Authors.jsp";
    private static final String AUTHOR_ADD = "add.jsp";
    private static final String AUTHOR_EDIT = "edit.jsp";
    
    
    private String driverClass;
    private String url;
    private String userName;
    private String password;

    
    @Inject
    private AuthorService authSrv;
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
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        
        HttpSession session = request.getSession();
        ServletContext ctx = request.getServletContext();
        
        response.setContentType("text/html;charset=UTF-8");
        
        String dest= "";
        String taskType = request.getParameter("taskType");
        
        configDbConnection();
        
        try {
            if(taskType.equals("viewAuthor")) {
                System.out.println("hi");
                request.setAttribute("authors", authSrv.getAuthorList());
                dest = AUTHORS;
            }
            else if (taskType.equals("deleteAuthor")){
                String authorId = (String)request.getParameter("id");
                authSrv.deleteAuthorById(authorId);
                this.refreshList(request, authSrv);
                dest= AUTHORS;
            }
            else if(taskType.equals("edit")){
                String authorId = (String)request.getParameter("id");
                Author author = authSrv.getAuthorById(authorId);
                request.setAttribute("author", author);
                dest = AUTHOR_EDIT;
            }
            else if(taskType.equals("add")){
                dest=AUTHOR_ADD;
            }
            else if(taskType.equals("save")){
                String authorName = request.getParameter("authorName");
                String authorId = request.getParameter("authorId");
                authSrv.saveOrUpdateAuthor(authorId, authorName);
                this.refreshList(request, authSrv);
                dest = AUTHORS;
            }
            
            else if(taskType.equals("new")){
                String authorName = request.getParameter("authorName");
                
                if(authorName!= null){
                    authSrv.saveOrUpdateAuthor(null, authorName);
                    
                }
                this.refreshList(request, authSrv);
                dest= AUTHORS;
            }
            else if(taskType.equals("cancel")){
                this.refreshList(request, authSrv);
                dest = AUTHORS;
            }
        }
            catch(Exception e){
                    
                    }
        RequestDispatcher view = request.getRequestDispatcher(dest);
        view.forward(request, response);
        
    

    }
    
    private void refreshList (HttpServletRequest request, AuthorService authService) throws ClassNotFoundException, SQLException{
        List<Author> authors = authSrv.getAuthorList();
        request.setAttribute("authors", authors);
    }
    private void configDbConnection() { 
        authSrv.getDao().initDao(driverClass, url, userName, password);   
    }
    
      @Override
    public void init() throws ServletException {
        // Get init params from web.xml
        driverClass = getServletContext().getInitParameter("db.driver.class");
        url = getServletContext().getInitParameter("db.url");
        userName = getServletContext().getInitParameter("db.username");
        password = getServletContext().getInitParameter("db.password");
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
        try{
        processRequest(request, response);
        } catch(ClassNotFoundException | SQLException ex) {
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
         try{
        processRequest(request, response);
        } catch(ClassNotFoundException | SQLException ex) {
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
