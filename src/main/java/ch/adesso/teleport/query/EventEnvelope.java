package ch.adesso.teleport.query;

public interface EventEnvelope<T extends CoreEvent> {

	// this method should be annotated with Avro @Union({}) in sub classes
	public T getEvent();
}
