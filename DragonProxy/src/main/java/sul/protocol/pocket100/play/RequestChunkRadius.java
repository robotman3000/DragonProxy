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

public class RequestChunkRadius extends Packet {

	public static final byte ID = (byte)68;

	public static final boolean CLIENTBOUND = false;
	public static final boolean SERVERBOUND = true;

	public int radius;

	public RequestChunkRadius() {}

	public RequestChunkRadius(int radius) {
		this.radius = radius;
	}

	@Override
	public int length() {
		return Buffer.varintLength(radius) + 1;
	}

	@Override
	public byte[] encode() {
		this._buffer = new byte[this.length()];
		this.writeBigEndianByte(ID);
		this.writeVarint(radius);
		return this._buffer;
	}

	@Override
	public void decode(byte[] buffer) {
		this._buffer = buffer;
		readBigEndianByte();
		radius=this.readVarint();
	}

	public static RequestChunkRadius fromBuffer(byte[] buffer) {
		RequestChunkRadius ret = new RequestChunkRadius();
		ret.decode(buffer);
		return ret;
	}

}
