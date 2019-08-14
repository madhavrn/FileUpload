package UploadAnyFile;

import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
// import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;


/**
 * Servlet implementation class UploadAnyFile
 */
@WebServlet(name = "UploadAnyFile", description = "This will take a file from any folder and uploads it to the server", urlPatterns = { "/UploadAnyFile" })
@MultipartConfig
public class UploadAnyFile extends HttpServlet {
    private final static Logger LOGGER =
            Logger.getLogger(UploadAnyFile.class.getCanonicalName());
	private static final long serialVersionUID = 1L;

	 /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // Create path components to save the file
        final String path = request.getParameter("destination");
        final Part filePart = request.getPart("file");
        final String fileName = getFileName(filePart);
        final String absfilename = fileName.substring(fileName.lastIndexOf("\\")+1);
        

        OutputStream out = null;
        InputStream filecontent = null;
        final PrintWriter writer = response.getWriter();

        try {
           // out = new FileOutputStream(new File(path + File.separator
             //       + fileName));
        	// important to strip the filename from the path/
            out = new FileOutputStream(new File(path + File.separator
                    + absfilename));
            filecontent = filePart.getInputStream();

            int read;
            final byte[] bytes = new byte[1024];

            while ((read = filecontent.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            writer.println("New file from  " + fileName + " created at " + path);
            LOGGER.log(Level.INFO, "File {0} being uploaded to {1}",
                    new Object[]{fileName, path});

        } catch (FileNotFoundException fne) {
            writer.println("You either did not specify a file to upload or are "
                    + "trying to upload a file to a protected or nonexistent "
                    + "location.");
            writer.println("<br/> ERROR: " + fne.getMessage());

            LOGGER.log(Level.SEVERE, "Problems during file upload. Error: {0}",
                    new Object[]{fne.getMessage()});
        } finally {
            if (out != null) {
                out.close();
            }
            if (filecontent != null) {
                filecontent.close();
            }
            if (writer != null) {
                writer.close();
            }
        }
    }

    private String trim(String fileName) {
		// TODO Auto-generated method stub
		return null;
	}

	private String getFileName(final Part part) {
        final String partHeader = part.getHeader("content-disposition");
        LOGGER.log(Level.INFO, "Part Header = {0}", partHeader);
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(
                        content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }	
    
    
/*
    public UploadAnyFile() {
        super();
        // TODO Auto-generated constructor stub
    }
*/
	/**
	 * @see Servlet#getServletConfig()
	 */
/*	public ServletConfig getServletConfig() {
		// TODO Auto-generated method stub
		return null;
	}
*/
	/**
	 * @see Servlet#getServletInfo()
	 */
    @Override
	public String getServletInfo() {
		// TODO Auto-generated method stub
		   return "Servlet that uploads files to a user-defined destination"; 
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		 //call Process Request
		 processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
/*
		System.out.println("The First call ");
		String name = request.getParameter("destination");
		System.out.println("The second call ");
		PrintWriter pw = response.getWriter();
		pw.write("\n </br>");
		pw.write("\n The destinations is </br>"+name);
		pw.write("\n </br>");
*/		
		//Do get call
		doGet(request, response);

		//		pw.write("\n </br>");
		//		System.out.println("The End call ");
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	/*
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	} 
	*/

}
