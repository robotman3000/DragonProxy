/*
 * This file was automatically generated by sel-utils and
 * released under the GNU General Public License version 3.
 *
 * License: https://github.com/sel-project/sel-utils/blob/master/LICENSE
 * Repository: https://github.com/sel-project/sel-utils
 * Generated from https://github.com/sel-project/sel-utils/blob/master/xml/protocol/pocket100.xml
 */
package sul.protocol.pocket100.play;

import sul.utils.*;

public class BossEvent extends Packet {

	public static final byte ID = (byte)75;

	public static final boolean CLIENTBOUND = true;
	public static final boolean SERVERBOUND = false;

	// event id
	public static final int ADD = 0;
	public static final int UPDATE = 1;
	public static final int REMOVE = 2;

	public long entityId;
	public int eventId;

	public BossEvent() {}

	public BossEvent(long entityId, int eventId) {
		this.entityId = entityId;
		this.eventId = eventId;
	}

	@Override
	public int length() {
		return Buffer.varlongLength(entityId) + Buffer.varuintLength(eventId) + 1;
	}

	@Override
	public byte[] encode() {
		this._buffer = new byte[this.length()];
		this.writeBigEndianByte(ID);
		this.writeVarlong(entityId);
		this.writeVaruint(eventId);
		return this._buffer;
	}

	@Override
	public void decode(byte[] buffer) {
		this._buffer = buffer;
		readBigEndianByte();
		entityId=this.readVarlong();
		eventId=this.readVaruint();
	}

	public static BossEvent fromBuffer(byte[] buffer) {
		BossEvent ret = new BossEvent();
		ret.decode(buffer);
		return ret;
	}

}
