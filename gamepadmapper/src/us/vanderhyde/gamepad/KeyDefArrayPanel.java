package us.vanderhyde.gamepad;

public class KeyDefArrayPanel extends java.awt.Panel
{
    private final KeyDefComponent[] lights;
    private boolean listeningForKeys;
    private int lastKeyPressed;
    private int lastButtonAction;

    public KeyDefArrayPanel(int numButtons)
    {
	lights = new KeyDefComponent[numButtons];
	for (int i=0; i<numButtons; i++)
	    {
		lights[i] = new KeyDefComponent();
		lights[i].addActionListener(new LightButtonListener(i));
		this.add(lights[i]);
	    }
	this.addKeyListener(new KeyCodeListener());
	listeningForKeys = false;
    }

    private void lightButtonActionPerformed(int index)
    {
	//Remember clicked button
	lastButtonAction = index;

	//Disable all buttons
	for (int i=0; i<lights.length; i++)
	    lights[i].disable();

	//Request focus
	this.requestFocus();

	//Start listening for key events
	listeningForKeys = true;
    }

    private void lightButtonActionFinished()
    {
	//Assign the key to the gamepad button

	//Tell the light the new key
	lights[lastButtonAction].setKey(lastKeyPressed);

	//Stop listening for key events
	listeningForKeys = false;

	//Enable all buttons
	for (int i=0; i<lights.length; i++)
	    lights[i].enable();
    }

    private class KeyCodeListener extends java.awt.event.KeyAdapter
    {
	@Override
	public void keyReleased(java.awt.event.KeyEvent evt)
	{
	    if (listeningForKeys)
	    {
		lastKeyPressed = evt.getKeyCode();
		lightButtonActionFinished();
	    }
	}
    }

    private class LightButtonListener implements java.awt.event.ActionListener
    {
	private int index;

	public LightButtonListener(int index)
	{
	    this.index=index;
	}

	@Override
	public void actionPerformed(java.awt.event.ActionEvent evt)
	{
	    lightButtonActionPerformed(index);
	}
    }
}