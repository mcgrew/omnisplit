package com.tjmcgrew.omnisplit.ui;

import edu.purdue.bbc.util.Settings;
import edu.purdue.bbc.util.Language;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Frame;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.json.*;
import javax.swing.JFrame;
import javax.swing.JWindow;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import com.tjmcgrew.omnisplit.util.SplitTime;
import org.apache.log4j.Logger;

/**
 * The main window class
 */
public class OmnisplitWindow extends JFrame {

  private JMenuBar menuBar;
  private JMenu fileMenu;
  private JMenuItem newFileMenuItem;
  private JMenuItem openFileMenuItem;
  private JMenuItem saveFileMenuItem;
  private JMenuItem closeFileMenuItem;
  private JMenuItem exitFileMenuItem;
  private JPanel splitPanel;

  private ArrayList elements = new ArrayList<SplitElement>();

  /**
   * Creates a new split window.
   */
  public OmnisplitWindow () {
    super();
    this.setLayout(new BorderLayout());
    this.splitPanel = new JPanel(new GridLayout(0, 1));
    this.splitPanel.setBackground(Color.DARK_GRAY);
    this.splitPanel.setOpaque(true);
    this.add(this.splitPanel, BorderLayout.NORTH);
    this.add(new JPanel(), BorderLayout.CENTER);

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

//    this.setupMenu( );

    // more temporary stuff
    this.splitPanel.add(new SplitElement(new SplitTime("Stage 1",  60000, 60000)));
    this.splitPanel.add(new SplitElement(new SplitTime("Stage 2", 120000, 60000)));
    this.splitPanel.add(new SplitElement(new SplitTime("Stage 3", 180000, 60000))); 
    this.splitPanel.add(new SplitElement(new SplitTime("Stage 4", 240000, 60000))); 
    this.splitPanel.add(new SplitElement(new SplitTime("Stage 5", 300000, 60000))); 
    // end temporary stuff

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

  private void setupMenu( ) {
    Language language = Settings.getLanguage( );

    this.menuBar = new JMenuBar( );
    this.fileMenu = new JMenu( language.get( "File" ));
    this.newFileMenuItem = new JMenuItem( 
      language.get( "New Game" )+"...", KeyEvent.VK_N );
    this.openFileMenuItem = new JMenuItem( 
      language.get( "Open Splits" ) + "...", KeyEvent.VK_O );
    this.saveFileMenuItem = new JMenuItem( 
      language.get( "Save" ), KeyEvent.VK_S );
    this.closeFileMenuItem = new JMenuItem( 
      language.get( "Close Splits" ), KeyEvent.VK_C );
    this.exitFileMenuItem = new JMenuItem( 
      language.get( "Exit" ), KeyEvent.VK_X );

    // FILE MENU
    this.fileMenu.setMnemonic( KeyEvent.VK_F );
    this.fileMenu.getAccessibleContext( ).setAccessibleDescription(
      language.get( "Perform file operations" ));
    this.fileMenu.add( this.newFileMenuItem );
    this.fileMenu.add( this.openFileMenuItem );
    this.fileMenu.add( this.saveFileMenuItem );
    this.fileMenu.add( this.closeFileMenuItem );
    this.fileMenu.add( this.exitFileMenuItem );

    this.menuBar.add( this.fileMenu );

    this.setJMenuBar( this.menuBar );

    this.addMenuListeners( );
  }

  private void addMenuListeners( ) {
//    this.newFileMenuItem.addActionListener( this );
  }


  public static OmnisplitWindow newWindow() {
    OmnisplitWindow window = new OmnisplitWindow();
    return window;
  }
}


