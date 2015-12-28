import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by timbauer on 12/7/15.
 */
public class ImageManager {
    public static ImageManager imageManager = null;

    public static String topIconStr = "Res/icons24/Top.png";
    public static String midIconStr = "Res/icons24/Mid.png";
    public static String adcIconStr = "Res/icons24/ADC.png";
    public static String suppIconStr = "Res/icons24/Support.png";
    public static String jungleIconStr = "Res/icons24/Jungle.png";

    public static String baronStr = "Res/icons24/BaronAttempt.png";
    public static String DragonStr = "Res/icons24/DragonAttempt.png";

    public static String riftHeraldStr = "Res/icons24/RiftHeraldAttempted.png";

    public static String towerLost = "Res/icons24/TowerLost.png";
    public static String towerTaken = "Res/icons24/TowerTaken.png";;


    public static String mapImageDirAndName = "Res/SRMap3.jpg";

    public static BufferedImage currentIcon;

    public static BufferedImage topIcon;
    public static BufferedImage midIcon;
    public static BufferedImage adcIcon;
    public static BufferedImage suppIcon;
    public static BufferedImage jungleIcon;

    public static BufferedImage SrMap;


    private ImageManager() throws IOException{
        topIcon = ImageIO.read(new File(topIconStr));
        midIcon = ImageIO.read(new File(midIconStr));
        adcIcon = ImageIO.read(new File(adcIconStr));
        suppIcon = ImageIO.read(new File(suppIconStr));
        jungleIcon = ImageIO.read(new File(jungleIconStr));
        SrMap = ImageIO.read(new File(mapImageDirAndName));

        currentIcon = jungleIcon;
    }

    public static ImageManager getImageManager()throws IOException{
        if(imageManager == null){
            imageManager = new ImageManager();
        }
        return imageManager;
    }

    public static BufferedImage getCurrentIcon(){
        return currentIcon;
    }

    public static void setCurrentIcon(int iconSetter){
        switch (iconSetter){
            case 1:
                currentIcon = topIcon;
                break;
            case 2:
                currentIcon = midIcon;
                break;
            case 3:
                currentIcon = adcIcon;
                break;
            case 4:
                currentIcon = suppIcon;
                break;
            case 5:
                currentIcon = jungleIcon;
                break;
            default:
                break;
        }
    }
}
