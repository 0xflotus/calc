package calculator;

import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JButton;

@SuppressWarnings ( "serial")
class Taste extends JButton
{

  int width   = 60, height = 50;
  int hgap    = 7, vgap = 7;
  int offsetX = 8, offsetY = 100;

  public Taste( String text, String name, int x, int y, int w, int h, ActionListener al )
  {
    super( text );
    this.setName( name );
    this.setFocusable( false );
    this.setSize( w * width, h * height );
    this.setLocation( offsetX + x * width + x * hgap, offsetY + y * height + y * vgap );
    this.addActionListener( al );
    this.setMargin( new Insets( 0, 0, 0, 0 ) );
    this.setFont( new Font( "Arial", Font.BOLD, 22 ) );
  }

}