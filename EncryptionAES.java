import javax.crypto.*;
import javax.crypto.spec.*;
import java.util.Base64;
import java.util.Scanner;
import java.io.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class EncryptionAES
{
    private SecretKey key;

    public EncryptionAES()
    {
        this.key = null;
    }

    public EncryptionAES(String fileName) throws Exception
    {
        this.key = null;
        initKey(fileName);
    }

    public SecretKey generateKey() throws Exception
    {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(128);
        return keyGen.generateKey();
    }

    public String encrypt(String text, SecretKey key) throws Exception
    {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedText = cipher.doFinal(text.getBytes());
        return Base64.getEncoder().encodeToString(encryptedText);
    }

    public String decrypt(String encryptedText, SecretKey key) throws Exception
    {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedText = Base64.getDecoder().decode(encryptedText);
        byte[] originalText = cipher.doFinal(decryptedText);
        return new String(originalText);
    }

    public void writeFile(String fileName, String line)
    {
        try
        {
            FileWriter fileWriter = new FileWriter(fileName, true);
            fileWriter.write(line);
            fileWriter.write(System.lineSeparator());
            fileWriter.close();
        }

        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public void readFile(SecretKey key) throws Exception
    {
        try
        {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("input.txt"));
            String line = bufferedReader.readLine();
            while(line != null)
            {
                String encryptedLine = encrypt(line, key);
                writeFile("encrypted.txt", encryptedLine);
                String decryptedLine = decrypt(encryptedLine, key);
                writeFile("decrypted.txt", decryptedLine);
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
        }

        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public boolean lineFound(String search, String fileName)
    {
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            Boolean found = false;
            String line;
            line = reader.readLine();
            while(line != null)
            {
                try
                {
                    if(search.equals(line))
                    {
                        found = true;
                        break;
                    }
                    line = reader.readLine();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
            reader.close();
            return found;
        }
        catch(IOException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public void inputIntoFile()
    {
        Scanner newScanner = new Scanner(System.in);
        String line;
        while(true)
        {
            System.out.print("\nDigite uma linha (para sair digite exit): ");
            line = newScanner.nextLine();
            if(line.equals("exit"))
            {
                break;
            }
            else if(!line.equals(""))
            {
                writeFile("input.txt", line);
            }
        }
        newScanner.close();
    }

    public void saveKey(SecretKey key, String fileName) throws IOException
    {
        byte[] encodedKey = key.getEncoded();
        String baseKey = Base64.getEncoder().encodeToString(encodedKey);
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(fileName)))
        {
            writer.write(baseKey);
        }
    }

    public SecretKey loadKey(String fileName) throws IOException
    {
        String baseKey;
        try(BufferedReader reader =  new BufferedReader(new FileReader(fileName)))
        {
            baseKey = reader.readLine();
        }
        byte[] decodedKey = Base64.getDecoder().decode(baseKey);
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
    }

    public void initKey(String fileName) throws Exception
    {
        File file = new File(fileName);
        if(file.exists())
        {
            this.key = loadKey(fileName);
            System.out.println("DEBUG: Key loaded: " + this.key.toString());
        }
        else
        {
            this.key = generateKey();
            saveKey(key, fileName);
            System.out.println("DEBUG: Key saved: " + this.key.toString());
        }
    }

    public SecretKey getKey()
    {
        return this.key;
    }
}
