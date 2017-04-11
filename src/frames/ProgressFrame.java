package frames;

import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import configs.Params;

public class ProgressFrame {
	public JFrame mainFrame = new JFrame("Main");
	JTextArea txtInfo = new JTextArea("SAOCR"+Params.Version);
	JLabel progress = new JLabel("00");
	Float progressValue = 0.0f;
	
	public static ProgressFrame instance;
	
	public ProgressFrame(){
		txtInfo.setEditable(false);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.getContentPane().setLayout(new BorderLayout());
		JScrollPane scroll = new JScrollPane(txtInfo);
		scrollBar = scroll.getVerticalScrollBar();
		mainFrame.add(scroll, BorderLayout.CENTER);
		progress.setFont(Params.progressdigitalfont);
		progress.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		mainFrame.add(progress, BorderLayout.NORTH);
//		mainFrame.setUndecorated(true);
//		com.sun.awt.AWTUtilities.setWindowOpaque(mainFrame,false);
//		((JPanel)mainFrame.getContentPane()).setOpaque(false);
		mainFrame.pack();
		mainFrame.setBounds(200, 200, 700, 200);
		mainFrame.setVisible(true);
		mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		instance = this;
	}
	
	public void addDigital(float value){
		progressValue += value;
		progress.setText(String.valueOf(progressValue));
		progress.paintImmediately(0, 0, progress.getSize().width, progress.getSize().height);
	}
	public void setDigital(float value){
		progressValue = value;
		progress.setText(String.valueOf(progressValue));
//		if (value == 100.0f){
//			mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//			mainFrame.dispose();
//		}
	}

	public void appendText(String newText){
		txtInfo.append(newText);
		scrollBar.setValue(scrollBar.getMaximum());
	}
	public void addText(String newText){
		System.out.println(newText);
		txtInfo.append("\n"+newText);
		scrollBar.setValue(scrollBar.getMaximum());
	}
	public void setText(String newText){
//		txtInfo.setText(newText);
	}
	
	private JScrollBar scrollBar;
}
