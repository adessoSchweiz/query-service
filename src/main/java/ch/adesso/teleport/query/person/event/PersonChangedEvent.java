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
public class PersonChangedEvent extends PersonEvent {

	@Nullable
	private String firstname;
	@Nullable
	private String lastname;
	@Nullable
	private String birthday;

}
