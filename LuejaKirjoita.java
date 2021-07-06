/**
 * 
 */
package apu;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


/**
 * @author laiti
 * @version 21 Jul 2020
 *
 */
public class LuejaKirjoita {
    private static final String statusLocation = "files/statuses.dat";
    private static final String lStatusLocation = "./files/statuses.dat";
    private static final String favouriteLocation = "files/favourites.dat";
    private static final String lFavouriteLocation = "./files/favourites.dat";
    

	
	
    /**
     * Lukee statusviestit tiedostosta
     * @return Lista statusviesteistä
     * @throws IOException Jos ongelmia
     */
    public static List<String> lueStatukset() throws IOException{
        
        List<String> lista = new ArrayList<String>();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(lStatusLocation), "UTF-8"));) {
            
            String line = reader.readLine();
            while (line != null) {
                lista.add(line);
                line = reader.readLine();
            }
        } 
        
        return lista;
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
    
    /**
     * Kirjoittaa statukset tiedostoon
     * @param lista Lista statuksista
     * @return True, jos onnistui
     * @throws IOException Jos ongelmia
     */
    public static boolean kirjoitaStatukset(List<String> lista) throws IOException{
        
        try(BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(lStatusLocation), "UTF-8"));) {
            
            var it = lista.iterator();
            while (it.hasNext()) {
                 writer.write(it.next().trim());
                if (it.hasNext()) writer.newLine();
            }
            
            
            return true;
        }
    }
    

    
    /**
     * Lukee statusviestit tiedostosta
     * @return Lista statusviesteistä
     * @throws IOException Jos ongelmia
     */
    public static List<String> lueFavourites() throws IOException{
        
        List<String> lista = new ArrayList<String>();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(lFavouriteLocation), "UTF-8"));) {
            
            String line = reader.readLine();
            while (line != null) {
                lista.add(line);
                line = reader.readLine();
            }
        } 
        
        return lista;
    }
    
    /**
     * Kirjoittaa statukset tiedostoon
     * @param lista Lista statuksista
     * @return True, jos onnistui
     * @throws IOException Jos ongelmia
     */
    public static boolean kirjoitaFavourites( List<String> lista) throws IOException{
        
        try(BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(lFavouriteLocation), "UTF-8"));) {
            
            var it = lista.iterator();
            while (it.hasNext()) {
                 writer.write(it.next().trim());
                if (it.hasNext()) writer.newLine();
            }
            
            
            return true;
        }
    }

    /**
     * Lisää yhden alkion tiedostoon
     * @param apujono URL YT-videoon
     * @throws IOException Jos tiedostoissa
     * @throws BiisiJoSoittolistassaException Jos biisi jo listalla
     */
    public static void kirjoitaJaLueFavourites(String apujono) throws IOException, BiisiJoSoittolistassaException {
        var lista = lueFavourites();
        
        if (!lista.contains(apujono)) {
            lista.add(apujono);
            kirjoitaFavourites(lista);
            return;
        } throw new BiisiJoSoittolistassaException("Listassa on jo tämä biisi vitun apina");
        
    }
    
    /**
     * Poistaa listalta biisin
     * @param apujono URL, joka poistetaan
     * @throws IOException Jos tiedostoissa
     * @throws BiisiJoSoittolistassaException Jos biisiä ei ole listalla
     */
    public static void poistaFavourites(String apujono) throws IOException, BiisiJoSoittolistassaException {
        var lista = lueFavourites();
        
        if (lista.contains(apujono)) {
            lista.remove(apujono);
            kirjoitaFavourites(lista);
            return;
        } throw new BiisiJoSoittolistassaException("Kyseistä kappaletta ei löydy");
        
    }


    public static void kirjoitaTiedosto(List<String> lista, String string) throws IOException {
        try(BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(string), "UTF-8"));) {
            
            var it = lista.iterator();
            while (it.hasNext()) {
                 writer.write(it.next().trim());
                if (it.hasNext()) writer.newLine();
            }
    }
    

}
}
