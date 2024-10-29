import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateHandler {
    private LocalDateTime date;
    private DateTimeFormatter formatter;
    private String dateString;

    public DateHandler(boolean isAmerican)
    {
        if(isAmerican)
        {
            this.date = LocalDateTime.now();
            this.formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
            this.dateString = date.format(this.formatter);
        }

        else
        {
            this.date = LocalDateTime.now();
            this.formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            this.dateString = date.format(this.formatter);
        }
    }

    public String getDate()
    {
        return this.dateString;
    }
}
