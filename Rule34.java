package r34;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author laiti
 * @version 22 Jul 2020
 *
 */
public class Rule34 {
    
    private static final String regex = "(file_url=\"https:\\/\\/).{3,120}(\"parent_id)";
    private static final String pituusURL = "https://rule34.xxx/index.php?page=dapi&s=post&q=index&limit=1&tags=";
    private static String URL = "https://rule34.xxx/index.php?page=dapi&s=post&q=index&limit=20&tags=";

	public static int min(int i1, int i2) {
		if (i1 >= i2 ) return i2;
		return i1;
	}

	public static List<String> lueTiedosto(String tiedostonNimi) throws IOException{
        
        List<String> lista = new ArrayList<String>();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(tiedostonNimi), "UTF-8"));) {
            
            String line = reader.readLine();
            while (line != null) {
                lista.add(line);
                line = reader.readLine();
            }
        } 
        
        return lista;
    }
	
    public static void kirjoitaTiedosto(List<String> lista, String string) throws IOException {
        try(BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(string), "UTF-8"));) {
            
            var it = lista.iterator();
            while (it.hasNext()) {
                 writer.write(it.next().trim());
                if (it.hasNext()) writer.newLine();
            }
    }

    /**
     * Hakee satunnaisen kuvan URLin RULE34 -sivustolta hakusanalla
     * @param hakusana Hakusana
     * @return URL
     * @throws IOException jos ongelmia
     */
    @SuppressWarnings("resource")
    public static String haeR34(String hakusana) throws IOException {
        
        String formatoitu = hakusana.replace(' ', '+');
        //Instantiating the URL class
        URL url = new URL(pituusURL+formatoitu);
        //Retrieving the contents of the specified page
        Scanner sc = new Scanner(url.openStream());
        //Instantiating the StringBuffer class to hold the result
        StringBuffer sb = new StringBuffer();
        while(sc.hasNext()) {
           sb.append(sc.next());
           //System.out.println(sc.next());
        }
        //Retrieving the String from the String Buffer object
        String result = sb.toString();
        
        String pregExp = "(postscount=\"\\d*\")";
        Pattern ppattern = Pattern.compile(pregExp);
        Matcher pmatcher = ppattern.matcher(result);
        
        pmatcher.find();
        String maara = result.substring(pmatcher.start() + 12, pmatcher.end() - 1);
        int lukum = 0;
        try {
            lukum = Integer.parseInt(maara);
        } catch (NumberFormatException e) {
            throw new IOException("Ongelmia postauksien lukumaaran lukemisen kanssa");
        }
        if (lukum == 0) {
            throw new IOException("Yhtään tulosta ei löytynyt - postcount");
        }
        
     
        int sivujenLukum = 1;
            if (lukum >= 20 ) sivujenLukum = lukum/20 ;
        
        int randomSivuID = 1;
        try {
            randomSivuID = ThreadLocalRandom.current().nextInt(1, min(sivujenLukum, 10000) +1);
            } catch (Exception e) {
                throw new IOException("Ongelmia satunnaisluvun kanssa" + e.getMessage());
            }
        
        
        URL uusiurl = null;
        if(randomSivuID == 1) uusiurl = new URL(URL + formatoitu);
        else {
            uusiurl = new URL(URL + formatoitu + "&pid=" +randomSivuID);
        }
        
        
        sc = new Scanner(uusiurl.openStream());
        
        //Instantiating the StringBuffer class to hold the result
        sb = new StringBuffer();
        while(sc.hasNext()) {
           sb.append(sc.next());
        }
        //Retrieving the String from the String Buffer object
        result = sb.toString(); 

        
        
        
        String regExp = regex;
        Pattern pattern = Pattern.compile(regExp);
        Matcher matcher =pattern.matcher(result);
        
        List<String> lista = new ArrayList<String>();
        while (matcher.find()) {

            //System.out.println("found: " + count + " : "
              //      + matcher.start() + " - " + matcher.end());
            
            lista.add(result.substring(matcher.start(), matcher.end()));
            
            
        }
        sc.close();
        var it = lista.iterator();
        List<String> formatoituLista = new ArrayList<String>();
        while (it.hasNext()) {
            var jono = it.next();
            int i = jono.indexOf('"');
            jono = jono.substring(i +1);
            i = jono.indexOf('"');
            jono = jono.substring(0,  i);
            formatoituLista.add(jono);
        }
        
        int randomNum = 0;
        try {
        randomNum = ThreadLocalRandom.current().nextInt(0, formatoituLista.size() );
        } catch (Exception e) {
            throw new IOException("Ongelmia satunnaisluvun kanssa" + e.getMessage());
        }
        var array = formatoituLista.toArray();
        if (randomNum >= 0 &&  array != null && randomNum < array.length && array.length != 0 ) return (String) array[randomNum];
        throw new IOException("API vittuilee tai ei tuloksia");
    }
    
    
    
    private static final String tagURL = "https://rule34.xxx/index.php?page=tags&s=list&pid=";
    private static final int maksimiSivut = 9519;
    private static final String tagiRegex = "(tags=.{1,20})(\">).{1,20}(<\\/a>)";
    private static final List<String> kokoLista = new ArrayList<String>();
   
    public static void lueTagit() throws IOException {
        try(BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("tagitesti.txt"), "UTF-8"));) {
            
            
        
        for (int j = 0; j <= maksimiSivut; j++){
            System.out.println(j);
        URL url = new URL(tagURL+ 50*j);
        //Retrieving the contents of the specified page
        Scanner sc = new Scanner(url.openStream());
        //Instantiating the StringBuffer class to hold the result
        StringBuffer sb = new StringBuffer();
        while(sc.hasNext()) {
           sb.append(sc.next());
           //System.out.println(sc.next());
        }
        //Retrieving the String from the String Buffer object
        String result = sb.toString();
        /*

    */
        
        String regExp = tagiRegex;
        Pattern pattern = Pattern.compile(regExp);
        Matcher matcher =pattern.matcher(result);
        
        List<String> lista = new ArrayList<String>();
        while (matcher.find()) {

            //System.out.println("found: " + count + " : "
              //      + matcher.start() + " - " + matcher.end());
            
            lista.add(result.substring(matcher.start(), matcher.end()));
            
            
        }
        sc.close();
        
        var it = lista.iterator();
        List<String> formatoituLista = new ArrayList<String>();
        while (it.hasNext()) {
            var jono = it.next();
            int i = jono.indexOf('>');
            jono = jono.substring(i +1);
            i = jono.indexOf('<');
            jono = jono.substring(0,  i);
            formatoituLista.add(jono);
        }
        
        var ite = formatoituLista.iterator();
        while (ite.hasNext())
            writer.write(ite.next() + "\r\n");
        }
        }
        
    }
    
    
    
    /**
     * 
     * @param args ei käytössä
     * @throws IOException jos ongelmaa
     */
    public static void main(String[] args) throws IOException {
        //System.out.println(haeR34("winston"));
        //lueTagit();
        //System.out.println(haeRandomilla());
        //testaaTagit();

        System.out.println(ThreadLocalRandom.current().nextInt(1,2));
            System.out.println(haeR34("silence"));
       
    }



    private static String rURL = "";
    private static final String wTagiPolku = "files/tagit_aakkostettu.txt";
    private static final String lTagiPolku = "./files/tagit_aakkostettu.txt";
    /**
     * Hakee satunnaisen kuvan r34-sivustolta
     * @return satunnaisen kuvan URL
     */
    public static String haeRandomilla() {
        //var regexPost = "(postscount=\"\\d*\")";
        try {
            var lista = lueTiedosto(lTagiPolku);
            var randomNum = ThreadLocalRandom.current().nextInt(0, lista.size() + 1 );
            try {
                rURL = haeR34(lista.get(randomNum));
            } catch (IOException e) {
                lista.remove(randomNum);
				kirjoitaTiedosto(lista, lTagiPolku);
                haeRandomilla();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return rURL;
    }
}

