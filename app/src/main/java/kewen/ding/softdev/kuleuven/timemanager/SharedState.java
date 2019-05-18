package kewen.ding.softdev.kuleuven.timemanager;

public class SharedState {
    private static SharedState _instance = new SharedState();
    private boolean failed = false;
    public static SharedState getInstance() {
        return _instance;
    }

    public void setFailed(boolean newState) {
        failed = newState;
    }

    public boolean getFailState() {
        return  this.failed;
    }
}
