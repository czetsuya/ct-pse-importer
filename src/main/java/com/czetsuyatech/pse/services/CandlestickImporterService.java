package com.czetsuyatech.pse.services;

import com.czetsuyatech.pse.persistence.entities.CandlestickEntity;
import com.czetsuyatech.pse.persistence.repositories.CandlestickRepository;
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
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
@Slf4j
public class CandlestickImporterService {

  @Inject
  CandlestickRepository candlestickRepository;

  @ConfigProperty(name = "folder.source")
  String srcFolder;

  @ConfigProperty(name = "folder.processed")
  String srcProcessed;

  @Transactional
  public void startImport() throws IOException {

    log.info("Import starts");

    try (Stream<Path> stream = Files.list(Paths.get(srcFolder))) {
      stream
          .filter(file -> !Files.isDirectory(file))
          .forEach(p -> processPath(p));
    }

    log.info("Import ends");
  }

  private void processPath(Path p) {

    try {
      Files.lines(p)
          .forEach(this::processLine);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private void processLine(String line) {

    String[] words = line.split(",");
    CandlestickEntity entity = CandlestickEntity.builder()
        .ticker(words[0])
        .createdAt(getDate(words[1]))
        .open(safeGetThenParse(words[2]))
        .high((safeGetThenParse(words[3])))
        .low((safeGetThenParse(words[4])))
        .close((safeGetThenParse(words[5])))
        .volume((safeGetThenParse(words[6])))
        .build();

    candlestickRepository.persist(entity);
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

        } catch(Exception e3) {
          df = DateTimeFormatter.ofPattern("MM/d/yyyy");
          return LocalDate.parse(d, df);
        }
      }
    }
  }

  private double safeGetThenParse(String val) {
    return Optional.ofNullable(val)
        .map(Double::parseDouble)
        .orElse(0d);
  }
}
