package us.vanderhyde.gamepad;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.awt.FlowLayout;
import java.awt.Button;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.KeyEvent;

public class KeyDefComponent extends java.awt.Panel
{
    private int vkey;
    private final Ellipse2D light;
    private boolean lightOn;
    private final Button changeButton;
    private final Label keyDescriptionLabel;
    private static final String UNDEFINED="Undefined";

    public KeyDefComponent()
    {
	vkey = KeyEvent.VK_UNDEFINED;

	this.setPreferredSize(new java.awt.Dimension(90,30));
	this.setLayout(new FlowLayout(FlowLayout.RIGHT));

	keyDescriptionLabel = new Label(UNDEFINED);
	Panel labelPanel=new Panel();
	labelPanel.setLayout(new java.awt.GridLayout(1,1));
	labelPanel.add(keyDescriptionLabel);
	this.add(labelPanel);

	changeButton = new Button("Change");
	this.add(changeButton);

	light = new Ellipse2D.Double(5,5,20,20);
	lightOn = false;
    }

    public void setKey(int keyCode)
    {
	vkey = keyCode;
	if (keyCode != KeyEvent.VK_UNDEFINED)
	    keyDescriptionLabel.setText(KeyEvent.getKeyText(keyCode));
	else
	    keyDescriptionLabel.setText(UNDEFINED);
    }

    public void turnLightOn()
    {
	lightOn = true;
    }

    public void turnLightOff()
    {
	lightOn = false;
    }

    public void addActionListener(java.awt.event.ActionListener l)
    {
	this.changeButton.addActionListener(l);
    }

    public void disable()
    {
	this.changeButton.setEnabled(false);
    }

    public void enable()
    {
	this.changeButton.setEnabled(true);
    }

    @Override
    public void update(Graphics g)
    {
	this.paint(g);
    }
    
    @Override
    public void paint(Graphics g)
    {
	Graphics2D g2=(Graphics2D)g;
	g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
			    RenderingHints.VALUE_ANTIALIAS_ON);
	if (lightOn)
	    g.setColor(Color.BLACK);
	else
	    g.setColor(Color.GREEN);
	g2.fill(light);
    }
}