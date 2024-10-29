import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class WriteFile {
    private String fileName;

    public WriteFile(String fileName) {
        this.fileName = fileName;
    }

    public void write(String text, String date) {
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(this.fileName, false), "UTF-8")) {
            writer.write("===============\n");
            writer.write(text + "\n");
            writer.write(date + "\n");
            writer.write("===============\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(String name, String text, String date) {
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(this.fileName, false), "UTF-8")) {
            writer.write(name + "\n");
            writer.write("===============\n");
            writer.write(date + "\n");
            writer.write(text + "\n");
            writer.write("===============\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
