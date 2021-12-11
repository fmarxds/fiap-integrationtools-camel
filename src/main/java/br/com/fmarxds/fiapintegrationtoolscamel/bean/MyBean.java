package br.com.fmarxds.fiapintegrationtoolscamel.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyBean {

    private Integer id;
    private String name;

}
