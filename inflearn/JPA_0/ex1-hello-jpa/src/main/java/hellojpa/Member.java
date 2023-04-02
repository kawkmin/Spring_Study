package hellojpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Member {

  @Id
  @GeneratedValue
  private Long id;
  @Column(name = "USERNAME")
  private String name;
//  @Column(name = "TEAM_ID")
//  private Long teamId;

  @ManyToOne
  @JoinColumn(name="TEAM_ID")
  private Team team;

  public Member() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Team getTeam() {
    return team;
  }

  public void changeTeam(Team team) {
    this.team = team;
    team.getMembers().add(this);
  }
}
