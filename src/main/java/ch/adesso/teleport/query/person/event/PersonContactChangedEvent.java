package ch.adesso.teleport.query.person.event;

import org.apache.avro.reflect.Nullable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
public class PersonContactChangedEvent extends PersonEvent {

	@Nullable
	private String mobil;
	@Nullable
	private String email;

}
