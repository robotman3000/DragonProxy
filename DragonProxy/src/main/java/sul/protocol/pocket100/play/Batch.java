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

public class Batch extends Packet {

	public static final byte ID = (byte)6;

	public static final boolean CLIENTBOUND = true;
	public static final boolean SERVERBOUND = true;

	public byte[] data;

	public Batch() {}

	public Batch(byte[] data) {
		this.data = data;
	}

	@Override
	public int length() {
		return Buffer.varuintLength(data.length) + data.length + 1;
	}

	@Override
	public byte[] encode() {
		this._buffer = new byte[this.length()];
		this.writeBigEndianByte(ID);
		this.writeVaruint((int)data.length); this.writeBytes(data);
		return this._buffer;
	}

	@Override
	public void decode(byte[] buffer) {
		this._buffer = buffer;
		readBigEndianByte();
		int bgrhdge=this.readVaruint(); data=this.readBytes(bgrhdge);
	}

	public static Batch fromBuffer(byte[] buffer) {
		Batch ret = new Batch();
		ret.decode(buffer);
		return ret;
	}

}
