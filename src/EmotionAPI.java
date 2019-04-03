/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emotionplayer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URI;
import java.util.ArrayList;
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
import org.json.JSONString;

/**
 *
 * @author Alma
 */
public class EmotionAPI {

    public EmotionAPI() {
    }
    

    public String GetEmotionResults(String uriBase, String faceAttributes, String subscriptionKey, String imageWithFaces) {
        System.out.println("Hello, Facial App testing 1:");
        HttpClient httpclient = HttpClientBuilder.create().build();
        String jsonString = "";
        try {
            URIBuilder builder = new URIBuilder(uriBase);

            // Request parameters. All of them are optional.
            builder.setParameter("returnFaceId", "false");
            builder.setParameter("returnFaceLandmarks", "false");
            builder.setParameter("returnFaceAttributes", faceAttributes);

            // Prepare the URI for the REST API call.
            URI uri = builder.build();
            HttpPost request = new HttpPost(uri);

            // Request headers.
            request.setHeader("Content-Type", "application/octet-stream");
            request.setHeader("Ocp-Apim-Subscription-Key", subscriptionKey);

            // Request body.
            BufferedImage image = null;
            File f = new File(imageWithFaces); //image file path
            image = ImageIO.read(f);

            //File file = new File(imageWithFaces);
            FileEntity reqEntityF
                    = new FileEntity(f, ContentType.APPLICATION_OCTET_STREAM);

            request.setEntity(reqEntityF);

            // Execute the REST API call and get the response entity.
            HttpResponse response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                // Format and display the JSON response.
                System.out.println("REST Response:\n");

                jsonString = EntityUtils.toString(entity).trim();
               
            }
        } catch (Exception e) {
            // Display error message.
            System.out.println(e.getMessage());
        }

        return jsonString;
    }
    
    public ArrayList emotionName(String jsonString, int strt) {
        ArrayList<String> emote = new ArrayList<>();
        String word = "";
        boolean con = false;
        int count=0;
        for (int i = 0; i < jsonString.length(); i++) {
            try {

                if ((int) jsonString.charAt(i) == 34 || con) {//if it sees a quote then it starts writing the word 
                    //and makes con true making it always enter the if statement 
                    con = true;
                    if ((int) jsonString.charAt(i) != 34) {//if not a quote it is okay to keep writing
                        word += jsonString.charAt(i);
                    } else if (word.length() > 0) {
                         ++count;
                        if (count>=strt){//start adding to list at start
                        emote.add(word);}
                       
                        word = "";
                        con = false;
                    }
                }
                
            } catch (Exception e) {
                System.out.println("Part a");
            }
        }
        return emote;
    }
    
    
    public  ArrayList emotionNum(String jsonString,int strt) {
        ArrayList<Double> number = new ArrayList<>();
        String num = "";
        boolean ncon = false;
        int count=0;
        for (int i = 0; i < jsonString.length(); i++) {
            if (((int) jsonString.charAt(i) < 58 && (int) jsonString.charAt(i) > 47) || ncon) {//if its a # then it a go
                ncon = true;
                if (((int) jsonString.charAt(i) > 58 || (int) jsonString.charAt(i) < 47) && (int) jsonString.charAt(i) != 46) {//add to list when not # or .
                     
                    if (count>strt){//start adding to list at start
                    number.add(Double.parseDouble(num));
                    }
                    count++;
                    num = "";
                    ncon = false;
                } else {
                    num += jsonString.charAt(i);
                }
            }
        }
        return number;
    }

}
