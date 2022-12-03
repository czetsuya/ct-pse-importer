package com.czetsuyatech.pse.persistence.entities;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@Table(uniqueConstraints = @UniqueConstraint(name = "uk_candlestick", columnNames = {"ticker", "created_at"}))
@Entity(name = "candlestick")
@SuperBuilder
public class CandlestickEntity {

  @Id
  @Column
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "created_at")
  private LocalDate createdAt;

  @Column
  private String ticker;

  @Column
  private double open;

  @Column
  private double close;

  @Column
  private double high;

  @Column
  private double low;

  @Column
  private double volume;
}
