package net.argus.io;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

import net.argus.util.ThreadManager;
import net.argus.util.debug.Debug;

public class Download {
	
	private URL mainURL;
	
	private boolean multiDown;
	private boolean newThread = true;
	
	private boolean finish;
	
	public Download(URL mainURL) {
		this.mainURL = mainURL;
	}
	
	public void download(String file, File path) {
		finish = false;

		ThreadManager.DOWNLOAD.start(new ThreadDownload(file, path));
		if(!newThread)
			while(!finish)
				ThreadManager.sleep(1);
 	}
	
	public synchronized byte[] getFileSync(String file) throws IOException {
		return getFile(file);
	}
	
	public byte[] getFile(String file) throws IOException {
		URL dowURL = new URL(mainURL + "/" + file);
		
		byte[] data = new byte[length(dowURL)];
		DataInputStream dataIn = new DataInputStream(dowURL.openStream());
		dataIn.readFully(data);
		dataIn.close();
		
		return data;
	}
	
	public void write(byte[] data, File path) throws IOException {
		if(!path.exists())
			new File(path.toString().substring(0, path.toString().lastIndexOf(File.separator))).mkdirs();
		
		
		this.write(data, new FileOutputStream(path));
	}
	
	public void write(byte[] data, OutputStream out) throws IOException {
		DataOutputStream dataOut = new DataOutputStream(out);
		
		dataOut.write(data);
		dataOut.close();
	}
	
	public int length(URL dowURL) {
		int length = 0;
		InputStream in;
		try {
			in = dowURL.openStream();
			
			while(in.read() != -1)
				length++;
			
			in.close();
		}catch(IOException e) {}
		
		return length;
	}
	
	public void setMultiDownload(boolean multiDown) {this.multiDown = multiDown;}
	public void setNewThread(boolean b) {this.newThread = b;}
	
	class ThreadDownload extends Thread {
		
		private String file;
		private File path;
		
		
		public ThreadDownload(String file, File path) {
			this.file = file;
			this.path = path;
		}
		
		@Override
		public void run() {
			byte[] data;
			
			try {
				data = multiDown?getFile(file):getFileSync(file);
				
				Debug.log("File downloaded");
				write(data, new File(path + "/" + file));
			}catch(Throwable e) {Debug.log("Download failed");}
			finish = true;
		}
		
	}
	
	public static void main(String[] args) throws MalformedURLException {
		Download down = new Download(new URL("https://argus.alwaysdata.net/"));
		down.download("common/file/client/res/logo.png", new File("D:\\Django\\bureau 1\\"));
		
	}

}
