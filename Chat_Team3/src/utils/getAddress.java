/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author ACER
 */
public class getAddress {

    public static String get_ip_Details(String ip) {
        try {
            URL url;
            url = new URL("https://ip.city/?ip=" + ip.substring(1).strip());
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            Pattern p = Pattern.compile("^<h3>City: (([A-z]+\\s?)+)</h3>$");
            while ((line = br.readLine()) != null) {
                Matcher m = p.matcher(line);
                if (m.matches()) {
                    line = m.group(1);
                    break;
                }
            }
            br.close();
            return line;
        } catch (MalformedURLException ex) {
            Logger.getLogger(getAddress.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(getAddress.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
