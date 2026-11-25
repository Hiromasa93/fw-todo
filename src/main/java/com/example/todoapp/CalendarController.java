package com.example.todoapp;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CalendarController {

    @GetMapping("/calendar")
    public String showCalendar(
            @RequestParam(name = "year", required = false) Integer year,
            @RequestParam(name = "month", required = false) Integer month,
            Model model) {
        YearMonth currentMonth = YearMonth.now();
        YearMonth displayedMonth = YearMonth.of(
                year == null ? currentMonth.getYear() : year,
                month == null ? currentMonth.getMonthValue() : month);

        LocalDate firstDate = displayedMonth.atDay(1);
        LocalDate lastDate = displayedMonth.atEndOfMonth();
        List<LocalDate> calendarDates = new ArrayList<>();

        // 月初の曜日分だけ、先頭に空の升目を追加します（日曜始まり）。
        for (int i = 0; i < firstDate.getDayOfWeek().getValue() % 7; i++) {
            calendarDates.add(null);
        }
        for (int day = 1; day <= displayedMonth.lengthOfMonth(); day++) {
            calendarDates.add(displayedMonth.atDay(day));
        }
        // 最後の週も7升にそろえます。
        while (calendarDates.size() % 7 != 0) {
            calendarDates.add(null);
        }

        YearMonth previousMonth = displayedMonth.minusMonths(1);
        YearMonth nextMonth = displayedMonth.plusMonths(1);
        model.addAttribute("year", displayedMonth.getYear());
        model.addAttribute("month", displayedMonth.getMonthValue());
        model.addAttribute("monthLabel", displayedMonth.getYear() + "年 " + displayedMonth.getMonthValue() + "月");
        model.addAttribute("firstDate", firstDate);
        model.addAttribute("lastDate", lastDate);
        model.addAttribute("calendarDates", calendarDates);
        model.addAttribute("previousYear", previousMonth.getYear());
        model.addAttribute("previousMonth", previousMonth.getMonthValue());
        model.addAttribute("nextYear", nextMonth.getYear());
        model.addAttribute("nextMonth", nextMonth.getMonthValue());
        return "calendar";
    }
}
