package com.tjmcgrew.omnisplit.ui;

import edu.purdue.bbc.util.Language;
import edu.purdue.bbc.util.Settings;
import edu.purdue.bbc.util.ProcessUtils;
import edu.purdue.bbc.ui.ContextMenu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import javax.json.*;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.event.MouseInputListener;

import com.tjmcgrew.omnisplit.util.Run;
import com.tjmcgrew.omnisplit.util.SplitTime;
import com.tjmcgrew.omnisplit.ui.RunPanel;
import com.tjmcgrew.omnisplit.io.SplitFile;
import org.apache.log4j.Logger;

/**
 * The main window class
 */
public class OmnisplitWindow extends JFrame implements MouseInputListener,
    KeyListener {

  private int oldMouseX, oldMouseY;
  private RunPanel runPanel;
  public static Color background = new Color(30, 30, 30);
  public ContextMenu menu;

  /**
   * Creates a new split window.
   */
  public OmnisplitWindow () {
    super("Omnisplit");
    this.setUndecorated(true);
    this.setLayout(new BorderLayout());

    this.addMouseListener(this);
    this.addMouseMotionListener(this);
    this.addKeyListener(this);

    Settings settings = Settings.getSettings( );
//    int width = settings.getInt( "window.main.width" );
//    int height = settings.getInt( "window.main.height" );
//    int x = Math.max( 0, Math.min( 
//      settings.getInt( "window.main.position.x" ), 
//      settings.getInt( "desktop.width" ) - width ));
//    int y = Math.max( 0, Math.min( 
//      settings.getInt( "window.main.position.y" ), 
//      settings.getInt( "desktop.height" ) - height ));
//    
    // temporary stuff
    int x = 400;
    int y = 200;
    int width = 300;
    int height = 500;
    // end temporary stuff
    this.setBounds( x, y, width, height );

    this.setVisible( true );
//    this.setExtendedState( 
//      settings.getInt( "window.main.frameState", Frame.NORMAL ));
    this.addWindowListener( new WindowAdapter( ) {
      public void windowClosing( WindowEvent e ) {
        OmnisplitWindow w = (OmnisplitWindow)e.getSource( );
//        Settings settings = Settings.getSettings( );
//        // make sure the window is not maximized before saving the
//        // size and position.
//        int state = w.getExtendedState( );
//        if ( state == Frame.NORMAL ) {
//          settings.setInt( "window.main.position.x", w.getX( ));
//          settings.setInt( "window.main.position.y", w.getY( ));
//          settings.setInt( "window.main.width", w.getWidth( ));
//          settings.setInt( "window.main.height", w.getHeight( ));
//        }
//        // discard the 'iconified' state
//        state &= ~Frame.ICONIFIED;
//        settings.setInt( "window.main.frameState", state ); 
        w.dispose( );
        OmnisplitWindow.checkWindowCount( );
      }
    });
		// This is stupid, but sometimes Java on Linux doesn't paint the initial 
		// window. This seems to fix it.
		ProcessUtils.sleep(1000);
    this.repaint( );

  }

  private static int getWindowCount( ) {
      int returnValue = 0;
      for( Window w : Window.getWindows( )) {
        if ( w.isShowing( ) && w instanceof OmnisplitWindow )
          returnValue++;
      }
      return returnValue;
  }

  private static void checkWindowCount( ) {
    int count = OmnisplitWindow.getWindowCount( );
    Logger logger = Logger.getLogger( OmnisplitWindow.class );
    logger.debug( "Detected " + count + " open windows" );
    if ( count < 1 ) {
      logger.debug( "Shutting Down" );
      System.exit( 0 );
    }
  }

  public void openFile() {
//		String lastOpenCSV = Settings.getSettings( ).getProperty( 
//			"history.open.last" );
		String lastOpenCSV = "./"; // temporary
		JFileChooser fc;
		if ( lastOpenCSV != null ) {
			fc = new JFileChooser( 
				new File( lastOpenCSV ).getParentFile( ));
		} else {
			fc = new JFileChooser( );
		}
		fc.setFileSelectionMode( JFileChooser.FILES_AND_DIRECTORIES );
//		fc.addChoosableFileFilter( new MetsignFileFilter( ));
		int options = fc.showOpenDialog( this );
		if ( options == JFileChooser.APPROVE_OPTION ) {
//			FileFilter fileFilter = fc.getFileFilter( );
			File selected = fc.getSelectedFile( );
      System.out.println(selected.toString());
//			Settings.getSettings( ).setProperty( "history.open.last", 
//				selected.getAbsolutePath( ));
			Run run = SplitFile.openJsonFile(selected);
			this.runPanel = new RunPanel(run);
			this.add(this.runPanel, BorderLayout.CENTER);
//      this.menu = new ContextMenu(this.runPanel);
		}
  }

  public void saveFile() {
    if (this.runPanel != null) {
    }
  }

  public void saveFileAs() {
    if (this.runPanel != null) {
    }
  }

  public static OmnisplitWindow newWindow() {
    OmnisplitWindow window = new OmnisplitWindow();
    return window;
  }

  public void mouseClicked(MouseEvent e) { } // not implemented
  public void mouseEntered(MouseEvent e) { } // not implemented
  public void mouseExited(MouseEvent e) { } // not implemented
  /**
	 * Remembers the position of the mouse click for movement and resizing.
	 * 
	 * @param e The MouseEvent associated with the press.
	 */
  public void mousePressed(MouseEvent e) {
    if (this.translateButton(e) == MouseEvent.BUTTON1) {
      this.oldMouseX = e.getPoint().x;
      this.oldMouseY = e.getPoint().y;
    }
  } // not implemented
  public void mouseReleased(MouseEvent e) { } // not implemented
  public void mouseMoved(MouseEvent e) { } // not implemented
  /**
	 * Since there is no decorations and thus no way to reposition/resize the 
	 * window, dragging the window anywhere will move it, and shift+drag to 
	 * resize.
	 * 
	 * @param e The MouseEvent associated with the drag.
	 */
  public void mouseDragged(MouseEvent e) { 
		int button = this.translateButton(e);
    if (button == MouseEvent.BUTTON3) {
			// open a context menu
		} else if (button == MouseEvent.BUTTON1) {
      if (e.isShiftDown()) {
        this.setSize(this.getWidth() + e.getPoint().x - oldMouseX,
          this.getHeight() + e.getPoint().y - oldMouseY);
        oldMouseX = e.getPoint().x;
        oldMouseY = e.getPoint().y;
      } else {
			 // TODO: This has some erratic movement on Linux. Look into it.
        this.setLocation(this.getX() + e.getPoint().x - oldMouseX,
          this.getY() + e.getPoint().y - oldMouseY);
      }
    }
  }
  public void keyPressed(KeyEvent e) {
    int code = e.getKeyCode();
    int modifiers = e.getModifiers();
    if ((modifiers & (InputEvent.CTRL_MASK | InputEvent.META_MASK)) != 0) {
      switch (code) {
        case KeyEvent.VK_O:
          System.out.println("Open file");
          this.openFile();
          break;
        case KeyEvent.VK_S:
          if ((modifiers & InputEvent.SHIFT_MASK) != 0) {
          this.saveFileAs();
          } else {
          this.saveFile();
          }
          break;
        case KeyEvent.VK_Q:
          this.dispose();
          break;
        default:
          break;
      }
    } else {
      if (this.runPanel != null) {
        Run run = this.runPanel.getRun();
        switch (code) {
          case KeyEvent.VK_SPACE:
            if (run.isPaused())
              run.resume();
            else if (run.isActive())
              run.next();
            else if (!run.isStarted())
              run.start();
            break;
          case KeyEvent.VK_BACK_SPACE:
            run.pause();
            break;
          default:
            break;
        }
      }
    }
  }
  public void keyReleased(KeyEvent e) {}
  public void keyTyped(KeyEvent e) {}

	private int translateButton(MouseEvent e) {
		// also check for NO_BUTTON since java events are broken in some
		// ipmlementations (e.g. in Wayland)
		int button = e.getButton();
		switch(button) {
			case MouseEvent.NOBUTTON:
			case MouseEvent.BUTTON1:
				if (!e.isMetaDown() && !e.isControlDown()) {
					return MouseEvent.BUTTON1;
				}
			case MouseEvent.BUTTON3:
				return MouseEvent.BUTTON3;
			default:
				return button;
		}
	}

}


