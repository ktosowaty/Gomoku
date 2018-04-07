package utility;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;

public class GetTimeFromServer {

    private String dateTime = "empty";

    private static final Logger LOGGER = Logger.getLogger(GetTimeFromServer.class.getName());

    public String getDateAndTime() {
        try {
            String TIME_SERVER = "1a.ncomputers.org";
            NTPUDPClient timeClient = new NTPUDPClient();
            InetAddress inetAddress = InetAddress.getByName(TIME_SERVER);
            TimeInfo timeInfo = timeClient.getTime(inetAddress);
            long returnTime = timeInfo.getReturnTime();
            Date time = new Date(returnTime);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd',' HH:mm:ss z");
            dateTime = dateFormat.format(time);
        } catch(UnknownHostException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        } catch(IOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
        return dateTime;
    }
}