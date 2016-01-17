import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.SwingUtilities;

public class Main {

	public static void main(String[] args) {
       
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				 new Gui();

			}
			});

		
		

	}

}
