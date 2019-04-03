/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emotionplayer;

/**
 *
 * @author Alma
 */
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.imageio.ImageIO;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class EmotionPlayer {

    // Replace <Subscription Key> with your valid subscription key.
    private static final String subscriptionKey = "01114165c8564a51b5703f424cd1489e";

    // NOTE: You must use the same region in your REST call as you used to
// obtain your subscription keys. For example, if you obtained your
// subscription keys from westus, replace "westcentralus" in the URL
// below with "westus".
//
// Free trial subscription keys are generated in the "westus" region. If you
// use a free trial subscription key, you shouldn't need to change this region.
    private static final String uriBase
            = "https://eastus.api.cognitive.microsoft.com/face/v1.0/detect";

    //working with my face from internet
    private static final String imageWithFaces
            = "C:\\Users\\Alma\\Pictures\\seniorproj\\angry.jpg";

//File file = new File("C:\Users\Account\Documents\ProjectFolder\ResourceFolder\image");
    //not working with my face from fath
//    File myFile=new File("C:\\Users\\Irfan\\Desktop\\testPics/irfHappy.jpg");
//    URL myUrl = myFile.toURI().toURL();
//
//
//
//URL nUrl = new File("C:\\Users\\Irfan\\Desktop\\Advanced data structures\\Emotion1\\irfHappy.JPG").toURI().toURL();
//
//
//    private static final String imageWithFaces =
//            "{\"url\":\"nUrl\"}";
    //all other attributes
//    private static final String faceAttributes =
//            "age,gender,headPose,smile,facialHair,glasses,emotion,hair,makeup,occlusion,accessories,blur,exposure,noise";
    //just emotion attribute
    private static final String faceAttributes
            = "emotion";
    
    private static final int nameStart=8;
    private static final int numberStart=3;


    public static void main(String[] args) throws URISyntaxException, IOException {
        EmotionAPI emote = new EmotionAPI();
        String emotionString = "";
        emotionString = emote.GetEmotionResults(uriBase, faceAttributes, subscriptionKey, imageWithFaces);
        String highestEmotion=(String)emote.emotionName(emotionString, nameStart).get(HighestEmo(emote.emotionNum(emotionString, numberStart)));
        System.out.println("Your Emotion is: "+highestEmotion);

    }

    public static int HighestEmo(ArrayList<Double> number) {
        double highestValue = 0;
        double currentV = 0;
        int saveIndex = 0;
        for (int i = 0; i < number.size(); i++) {
            currentV = number.get(i);
            if (highestValue < currentV) {
                highestValue = currentV;
                saveIndex = i;
            }
        }

        return saveIndex;
    }

}
