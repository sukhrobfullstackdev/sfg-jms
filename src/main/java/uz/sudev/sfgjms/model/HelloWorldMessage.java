package uz.sudev.sfgjms.model;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HelloWorldMessage implements Serializable {
    static final long serialVersionUID = -6703826490277916847L;
    private UUID id;
    private String message;
}
