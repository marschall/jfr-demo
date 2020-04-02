package com.github.marschall.jfrdemo.entities;

import static javax.persistence.GenerationType.SEQUENCE;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity(name = "CHILD_ENTITY")
public class ChildEntity {

  @Id
  @Column(name = "CHILD_ID")
  @GeneratedValue(
      strategy = SEQUENCE,
      generator = "child-sequence-generator"
  )
  @SequenceGenerator(
      name = "child-sequence-generator",
      sequenceName = "SEQ_CHILD_ID",
      allocationSize = 1
  )
  private Long childId;

  @Column(name = "PARENT_ID")
  private Long parentId;

  public Long getChildId() {
    return this.childId;
  }

  public void setChildId(Long childId) {
    this.childId = childId;
  }

  public Long getParentId() {
    return this.parentId;
  }

  public void setParentId(Long parentId) {
    this.parentId = parentId;
  }

}