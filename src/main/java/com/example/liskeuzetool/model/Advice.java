package com.example.liskeuzetool.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "advices")
public class Advice {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String email;

  @Column(length=4000)
  private String background;

  private String role;

  @ElementCollection
  @CollectionTable(name="advice_interests", joinColumns=@JoinColumn(name="advice_id"))
  @Column(name="interest")
  private List<String> interests;

  @Column(length=8000)
  private String modulesJson;

  private Boolean wantsContact;

  private String adviceDate;

  @Column(length=8000)
  private String advicePlain;

  public Advice() {}

  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public String getName() { return name; }
  public void setName(String name) { this.name = name; }
  public String getEmail() { return email; }
  public void setEmail(String email) { this.email = email; }
  public String getBackground() { return background; }
  public void setBackground(String background) { this.background = background; }
  public String getRole() { return role; }
  public void setRole(String role) { this.role = role; }
  public List<String> getInterests() { return interests; }
  public void setInterests(List<String> interests) { this.interests = interests; }
  public String getModulesJson() { return modulesJson; }
  public void setModulesJson(String modulesJson) { this.modulesJson = modulesJson; }
  public Boolean getWantsContact() { return wantsContact; }
  public void setWantsContact(Boolean wantsContact) { this.wantsContact = wantsContact; }
  public String getAdviceDate() { return adviceDate; }
  public void setAdviceDate(String adviceDate) { this.adviceDate = adviceDate; }
  public String getAdvicePlain() { return advicePlain; }
  public void setAdvicePlain(String advicePlain) { this.advicePlain = advicePlain; }
}
