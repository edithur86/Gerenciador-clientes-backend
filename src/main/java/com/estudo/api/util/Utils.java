package com.estudo.api.util;

import com.estudo.api.commons.Translator;
import com.estudo.api.services.exceptions.ObjectNotFoundException;
import org.joda.time.Period;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Utils {

    /**
     * Verifica se a data esta vigente com a data de inicio
     * <p>
     * Return boolean
     */
    public static boolean isVigente(Date date, Date dtInicio) {

        try {
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            date = format.parse(format.format(date));
            dtInicio = format.parse(format.format(dtInicio));

            return date.compareTo(dtInicio) >= 0;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean dateIsValid(String date) {
        date = completaData(date);
        Period p = new Period(new Date().getTime(), parseToDate(date).getTime());

        if (new Date().compareTo(parseToDate(date)) <= 0) {
            return true;
        } else {
            if (p.getYears() == 0 && p.getMonths() == 0) {
                return true;
            }
        }
        return false;
    }

    public static String formatarMensagemTexto(String msg, Integer tamanho, String tipo) {
        if (tipo.equals("S")) {
            return removeAccents(msg + " ".repeat(tamanho)).substring(0, tamanho);
        } else {
            return removeAccents("0".repeat(tamanho)).substring(0, tamanho - msg.length()) + msg;
        }
    }

    public static String removeAccents(String str) {
        str = Normalizer.normalize(str, Normalizer.Form.NFD);
        str = str.replaceAll("[^\\p{ASCII}]", "");
        return str;
    }

    public static String formatarMensagemNumerico(BigDecimal valor, Integer tamanho, Integer qtdDecimais) {
        String str = valor.setScale(qtdDecimais, RoundingMode.HALF_EVEN).toString().replace(",", "").replace(".", "");
        return ("0".repeat(tamanho)).substring(0, tamanho - str.length()) + str;
    }

    private static boolean validDateUS(String dateStr) {
        try {
            SimpleDateFormat dateUS = new SimpleDateFormat("yyyy-MM-dd");
            dateUS.parse(dateStr);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    private static boolean validDateBR(String dateStr) {
        try {
            SimpleDateFormat dateBR = new SimpleDateFormat("dd/MM/yyyy");
            dateBR.parse(dateStr);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    public static String completaData(String date) {
        if (date.contains("/")) {
            if (date.length() < 8) {
                date = "01/" + date;
            }
        }
        if (date.contains("-")) {
            if (date.length() < 8) {
                date = date + "-01";
            }
        }
        return date;
    }

    public static Date parseToDate(String dateStr) {

        try {
            if (dateStr.contains("-")) {
                DateFormat formatUs = new SimpleDateFormat("yyyy-MM-dd");
                Date date = formatUs.parse(dateStr);

                DateFormat formatBR = new SimpleDateFormat("dd-MM-yyyy");
                String dataFormatada = formatBR.format(date);
                return formatBR.parse(dataFormatada);
            } else {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                return dateFormat.parse(dateStr);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static long calculoDiasDate(Date data1, Date data2) {

        return ((data2.getTime() - data1.getTime()) / 1000 / 60 / 60 / 24);

    }



    public static Date zerarHoras(Date data) {
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try {
            return format.parse(format.format(data));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return data;
    }
    public static Date zerarHorasDate(Date data) {
        DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        try {
            return format.parse(format.format(data));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static String DataHoje() {
        Date dateTime = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String a = dateFormat.format(dateTime);
        return a;
    }

    public static String dateToString(Date data) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(data);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);

        String monthStr = String.valueOf(month);
        if (month < 10) {
            monthStr = "0" + month;
        }

        String dayStr = String.valueOf(day);
        if (day < 10) {
            dayStr = "0" + day;
        }

        return dayStr + "/" + monthStr + "/" + year;

    }

    public static LocalDateTime convertToLocalDateTimeViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    public static LocalDateTime convertToLocalDateTimeViaMilisecond(Date dateToConvert) {
        return Instant.ofEpochMilli(dateToConvert.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    public static String getMonthOfDate(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.MONTH, c.get(Calendar.MONTH) - 1);
        DateFormat df = new SimpleDateFormat("MMMM");
        return df.format(c.getTime());

    }

    public static String getMonthString(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.MONTH, c.get(Calendar.MONTH));
        DateFormat df = new SimpleDateFormat("MMMM");
        return df.format(c.getTime());

    }

    public static String getYearofDate(Date date) {
        DateFormat df = new SimpleDateFormat("YYYY");
        return df.format(date);
    }

    public static String getDateStringFromLocalDateTime(LocalDateTime localDateTime) {
        Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        return dateToString(date);
    }

    public static String getTimeStringFromLocaDateTime(LocalDateTime localDateTime) {
        int hour = localDateTime.getHour();
        int minute = localDateTime.getMinute();
        int second = localDateTime.getSecond();
        return hour + ":" + minute + ":" + second;
    }

    public static LocalDateTime convertStringToLocalDateFormat(String toConverter) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(toConverter, formatter);
    }



    public static Date getDataVencimentoPlanoPrePago(Date vencimento) {
        Calendar c = Calendar.getInstance();
        c.setTime(vencimento);
        c.add(Calendar.DAY_OF_MONTH, 5);
        return c.getTime();
    }

    public static boolean datePrePagoIsValid(Date date) {
        if (date.compareTo(new Date()) >= 0) {
            return true;
        }
        return false;
    }
}
