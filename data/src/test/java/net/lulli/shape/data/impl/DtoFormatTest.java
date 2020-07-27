package net.lulli.shape.data.impl;

import org.junit.Test;

import net.lulli.shape.data.api.IDto;
import net.lulli.shape.data.dto.Dto;
import net.lulli.shape.data.dto.DtoField;
import net.lulli.shape.data.dto.DtoFormat;
import net.lulli.shape.data.dto.field.IntegerField;
import net.lulli.shape.data.dto.field.TextField;

public class DtoFormatTest {

  @Test
  public void build() {
    TextField textType = new TextField(128);
    
    DtoFormat dtoFormat = DtoFormat.of("someTable")
          .withText("myField", 128)
          .withInteger("intfield", 356)
          .withField(new DtoField("some-name", new IntegerField()));
    
    IDto dto = Dto.fromFormat(dtoFormat);
  }
}
