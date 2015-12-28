/**
 * Created by timbauer on 12/7/15.
 */
public class EventTimer {
    final private static long initialTime = System.currentTimeMillis();

    private static EventTimer eventTimer = null;
    private static long currentTimeDelta = 0;

    private EventTimer(){
        //singleton
    }

    public static EventTimer getEventTimer(){
        if (eventTimer == null){
            eventTimer = new EventTimer();
        }
        return eventTimer;
    }

    public static long getCurrentTimeDelta(){
        currentTimeDelta = System.currentTimeMillis() - initialTime;
        return currentTimeDelta;
    }
}
