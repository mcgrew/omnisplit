package com.tjmcgrew.omnisplit.ui;

import edu.purdue.bbc.util.Language;
import edu.purdue.bbc.util.Settings;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.json.*;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.event.MouseInputListener;

import com.tjmcgrew.omnisplit.util.SplitTime;
import com.tjmcgrew.omnisplit.ui.RunPanel;
import org.apache.log4j.Logger;

/**
 * The main window class
 */
public class OmnisplitWindow extends Window implements MouseInputListener {

  private int oldMouseX, oldMouseY;
  private RunPanel runPanel;

  /**
   * Creates a new split window.
   */
  public OmnisplitWindow () {
    super(null);
    this.setLayout(new BorderLayout());
    this.runPanel = new RunPanel(null);
    this.add(this.runPanel, BorderLayout.CENTER);

    this.addMouseListener(this);
    this.addMouseMotionListener(this);

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
    this.repaint( );
    this.validate( );

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

  public static OmnisplitWindow newWindow() {
    OmnisplitWindow window = new OmnisplitWindow();
    return window;
  }

  public void mouseClicked(MouseEvent e) { } // not implemented
  public void mouseEntered(MouseEvent e) { } // not implemented
  public void mouseExited(MouseEvent e) { } // not implemented
  public void mousePressed(MouseEvent e) {
    if (e.getButton() == 1) {
      this.oldMouseX = e.getPoint().x;
      this.oldMouseY = e.getPoint().y;
    }
  } // not implemented
  public void mouseReleased(MouseEvent e) { } // not implemented
  public void mouseMoved(MouseEvent e) { } // not implemented
  public void mouseDragged(MouseEvent e) { 
    if (e.getButton() == 1) {
      this.setLocation(this.getX() + e.getPoint().x - oldMouseX,
        this.getY() + e.getPoint().y - oldMouseY);
    }
  }

}


