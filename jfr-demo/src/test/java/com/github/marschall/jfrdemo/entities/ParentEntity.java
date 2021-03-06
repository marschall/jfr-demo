package com.github.marschall.jfrdemo.entities;

import static javax.persistence.GenerationType.SEQUENCE;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

@Entity(name = "PARENT_ENTITY")
public class ParentEntity {

  @Id
  @Column(name = "PARENT_ID")
  @GeneratedValue(
      strategy = SEQUENCE,
      generator = "parent-sequence-generator"
  )
  @SequenceGenerator(
      name = "parent-sequence-generator",
      sequenceName = "SEQ_PARENT_ID",
      allocationSize = 1
  )
  private Long parentId;

  @OneToMany
  @JoinColumn(name = "PARENT_ID")
  private Set<ChildEntity> children = new HashSet<>();

  public Long getParentId() {
    return this.parentId;
  }

  public void setParentId(Long parentId) {
    this.parentId = parentId;
  }

  public Set<ChildEntity> getChildren() {
    return this.children;
  }

  public void setChildren(Set<ChildEntity> children) {
    this.children = children;
  }

  public void addChild(ChildEntity child) {
    this.children.add(child);
  }

}