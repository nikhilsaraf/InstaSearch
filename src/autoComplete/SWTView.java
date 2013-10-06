/**
 * 
 */
package autoComplete;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * @author Nikhil Saraf
 *
 */
public class SWTView {
	private static SWTView singleton = null; 
	
	private Display display;
	private Shell shell;
	
	private Text searchBox;
	
	private Label label;
	
	private Node head;
	
	private SWTView() {
		display = new Display();
		shell = new Shell(display);
	}
	
	public static SWTView getInstance() {
		if(singleton == null) {
			singleton = new SWTView();
		}
		
		return singleton;
	}
	
	private void performSearch() {
		String searchResult = head.search(searchBox.getText(), 0);
		AutoComplete.log("Auto-complete result: " + searchResult);
		
		label.setVisible(true);
	    label.setSize(300, 200);
		
		label.setText(searchResult);	
	}
	
	public void init(Node head) {
		this.head = head;
		shell.setLayout(new GridLayout());
		shell.setText("Nikhil Saraf's Instant Auto-Complete Dictionary Applciation (Beta Version)");
		
		Label title = new Label(shell, SWT.CENTER);
		title.setText("Instant Auto-Complete Dictionary");
		
		Label numWordsLabel = new Label(shell, SWT.CENTER);
		numWordsLabel.setText("Words in Dictionary: " + Dictionary.getSize());
		
		Label newLine = new Label(shell, SWT.CENTER);
		newLine.setText("\n");
		
		Label statement = new Label(shell, SWT.CENTER);
		statement.setText("Enter your search words");
		
		searchBox = new Text(shell, SWT.SINGLE);
		
		label = new Label(shell, SWT.CENTER);
	}
	
	public void run() {
		searchBox.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent arg0) {
				performSearch();
			}
		});
		
	    shell.open();
	    
	    while (!shell.isDisposed()) {
	      if (!display.readAndDispatch())
	        display.sleep();
	    }
	    display.dispose();
	}
}