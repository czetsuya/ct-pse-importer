package com.czetsuyatech.pse.services;

import com.czetsuyatech.pse.services.pojos.Candlestick;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.stream.Stream;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
@Slf4j
public class CandlestickImporterService {

  @Inject
  CandlestickService candlestickService;

  @ConfigProperty(name = "folder.source")
  String srcFolder;

  @ConfigProperty(name = "folder.processed")
  String srcProcessed;

  static int filesProcessed;

  @Transactional(TxType.NOT_SUPPORTED)
  public void startImport() throws IOException {

    log.info("Import starts");

    try (Stream<Path> stream = Files.list(Paths.get(srcFolder))) {
      stream
          .parallel()
          .filter(file -> !Files.isDirectory(file))
          .filter(e -> getExtension(e.getFileName().toString()).equals("csv"))
          .forEach(p -> processPath(p));
    }

    log.info("Import ends with {} files processed", filesProcessed);
  }

  private static String getExtension(String fileName) {

    String extension = "";

    int index = fileName.lastIndexOf('.');
    if (index > 0) {
      extension = fileName.substring(index + 1);
    }

    return extension.toLowerCase();
  }

  private void processPath(Path p) {

    try {
      Files.lines(p)
          .parallel()
          .forEach(this::processLine);
      filesProcessed++;

    } catch (Exception e) {
      log.error("Failed parsing file={} with error={}", p.getFileName(), e.getMessage());
    }
  }

  private void processLine(String line) {

    String[] words = line.split(",");
    try {
      candlestickService.create(getCandlestick(words));

    } catch (ArrayIndexOutOfBoundsException e) {
      throw new RuntimeException("Invalid line format=" + line);
    }
  }

  private Candlestick getCandlestick(String words[]) throws ArrayIndexOutOfBoundsException {

    return Candlestick.builder()
        .ticker(words[0])
        .createdAt(getDate(words[1]))
        .open(safeGetThenParse(words[2]))
        .high((safeGetThenParse(words[3])))
        .low((safeGetThenParse(words[4])))
        .close((safeGetThenParse(words[5])))
        .volume((safeGetThenParse(words[6])))
        .build();
  }

  private LocalDate getDate(String d) {

    DateTimeFormatter df = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    try {
      return LocalDate.parse(d, df);

    } catch (Exception e1) {
      try {
        df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(d, df);

      } catch (Exception e2) {
        try {
          df = DateTimeFormatter.ofPattern("MM-dd-yyyy");
          return LocalDate.parse(d, df);

        } catch (Exception e3) {
          try {
            df = DateTimeFormatter.ofPattern("MM/d/yyyy");
            return LocalDate.parse(d, df);

          } catch (Exception e4) {
            try {
              df = DateTimeFormatter.ofPattern("M/dd/yyyy");
              return LocalDate.parse(d, df);

            } catch (Exception e5) {
              df = DateTimeFormatter.ofPattern("M/d/yyyy");
              return LocalDate.parse(d, df);
            }
          }
        }
      }
    }
  }

  private double safeGetThenParse(String val) {

    try {
      return Optional.ofNullable(val)
          .map(Double::parseDouble)
          .orElse(0d);

    } catch (NumberFormatException e) {
      throw new RuntimeException("Cannot parse value=" + val);
    }
  }
}
