
package com.srm.curahealth.pages;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.srm.curahealth.base.BasePage;

public class HistoryPage extends BasePage {

    private static final By HISTORY_HEADING = By.xpath("//section[@id='history']//h2[contains(normalize-space(),'History')]");
    private static final By HISTORY_PANELS = By.cssSelector("#history .panel");
    private static final DateTimeFormatter APP_DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final Pattern DATE_PATTERN = Pattern.compile("\\b\\d{2}/\\d{2}/\\d{4}\\b");

    public HistoryPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        return isDisplayed(HISTORY_HEADING);
    }

    public int getAppointmentCount() {
        return driver.findElements(HISTORY_PANELS).size();
    }

    public String getHistoryContent() {
        return waitForElement(By.id("history")).getText().trim();
    }

    public boolean containsAppointment(String facility, String visitDate) {
        return getAppointmentSummaries().stream()
                .anyMatch(summary -> summary.contains(facility) && summary.contains(visitDate));
    }

    public String getMostRecentAppointmentSummary() {
        List<String> summaries = getAppointmentSummaries();
        return summaries.isEmpty() ? "" : summaries.get(0);
    }

    public List<String> getAppointmentDates() {
        List<String> dates = new ArrayList<>();
        for (WebElement panel : driver.findElements(HISTORY_PANELS)) {
            Matcher matcher = DATE_PATTERN.matcher(panel.getText());
            if (matcher.find()) {
                dates.add(matcher.group());
            }
        }
        return dates;
    }

    public boolean areAppointmentsSortedByDate() {
        List<LocalDate> actualDates = getAppointmentDates().stream()
                .map(date -> LocalDate.parse(date, APP_DATE_FORMAT))
                .collect(Collectors.toList());
        if (actualDates.size() < 2) {
            return true;
        }

        List<LocalDate> descendingDates = new ArrayList<>(actualDates);
        descendingDates.sort(Comparator.reverseOrder());

        List<LocalDate> ascendingDates = new ArrayList<>(actualDates);
        ascendingDates.sort(Comparator.naturalOrder());

        return actualDates.equals(descendingDates) || actualDates.equals(ascendingDates);
    }

    private List<String> getAppointmentSummaries() {
        return driver.findElements(HISTORY_PANELS).stream()
                .map(WebElement::getText)
                .map(String::trim)
                .collect(Collectors.toList());
    }
}
