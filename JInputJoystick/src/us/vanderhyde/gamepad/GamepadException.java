package us.vanderhyde.gamepad;


public class GamepadException extends RuntimeException
{
    public GamepadException()
    {
    }

    public GamepadException(String message)
    {
        super(message);
    }

    public GamepadException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public GamepadException(Throwable cause)
    {
        super(cause);
    }
}
