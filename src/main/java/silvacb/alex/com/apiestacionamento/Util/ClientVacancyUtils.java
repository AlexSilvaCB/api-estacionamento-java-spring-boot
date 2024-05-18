package silvacb.alex.com.apiestacionamento.Util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClientVacancyUtils {

    public static LocalDateTime converterStringData0(String data){
            DateTimeFormatter parser = new DateTimeFormatterBuilder()
                    .appendPattern("yyyy-MM-dd")
                    .parseDefaulting(ChronoField.HOUR_OF_DAY, 0).toFormatter();

            LocalDateTime dateTime = LocalDateTime.parse(data, parser);
            return dateTime;
    }

    public static LocalDateTime converterStringData23(String data){
        DateTimeFormatter parser = new DateTimeFormatterBuilder()
                .appendPattern("yyyy-MM-dd")
                .parseDefaulting(ChronoField.HOUR_OF_DAY, 23).toFormatter();

        LocalDateTime dateTime = LocalDateTime.parse(data, parser);
        return dateTime;
    }

}
