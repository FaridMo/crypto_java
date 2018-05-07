/**
 * @author Farid Mohamed Hassan
 */
package genkey;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class CryptoUtil {
		
	public static SecretKey keyGen(String algo,int taille) throws NoSuchAlgorithmException {
		/*  récuperation de l'instance engine  */
		KeyGenerator kg = KeyGenerator.getInstance("AES"); 
		
		/* Initialisation de l'instance */
		kg.init(taille);
		
		/* Run : exploitation */
		SecretKey k = kg.generateKey();
		String alg= k.getAlgorithm();
		alg=algo;
//		byte[] contenu = k.getEncoded(); 
		
		return k;
	}
	
	
	/* Méthode pour vérifier la sauvegarde d'une clé générée */
	public static boolean save_key(SecretKey k, String chemin_fichier) throws NoSuchAlgorithmException{
		boolean res = false;
		
		try {
			
			if(!chemin_fichier.isEmpty()){
				FileOutputStream fop = new FileOutputStream(chemin_fichier);
				ObjectOutputStream oos = new ObjectOutputStream(fop);
				byte[] contenu = keyGen("AES",128).getEncoded();
				oos.writeBytes(new String(contenu));

				SecretKey secretKey = new SecretKeySpec(contenu,"AES");
	
				oos.writeObject(secretKey);
				
				res=true;
				}	
		}catch(FileNotFoundException e){
			System.out.println("Le chemin n'est pas valide");
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		return res;
	}
	

	
	public static SecretKey receive_key(String chemin_fichier) throws NoSuchAlgorithmException{
		try {
			FileInputStream fis = new FileInputStream(chemin_fichier);
			ObjectInputStream ois = new ObjectInputStream(fis);
			SecretKey k = keyGen("AES", 128);
			ois.readBoolean();
			
		} catch (FileNotFoundException e) {
			System.out.println("Le fichier n'existe pas !");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return keyGen("AES", 128);
		
	}
	
	public static void main(String[] args) throws NoSuchAlgorithmException {
		System.out.println(save_key(keyGen("AES", 128), "fichiers/keygen.ser"));
		System.out.println(receive_key("fichiers/keygen.ser").getEncoded());
		
	}
	
	
}
