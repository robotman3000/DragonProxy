/*
 * This file was automatically generated by sel-utils and
 * released under the GNU General Public License version 3.
 *
 * License: https://github.com/sel-project/sel-utils/blob/master/LICENSE
 * Repository: https://github.com/sel-project/sel-utils
 * Generated from https://github.com/sel-project/sel-utils/blob/master/xml/protocol/pocket100.xml
 */
package sul.protocol.pocket100.types;

import sul.utils.*;

public class Recipe extends Packet {

	// type
	public static final int SHAPELESS = 0;
	public static final int SHAPED = 1;
	public static final int FURNACE = 2;
	public static final int FURNACE_DATA = 3;
	public static final int MULTI = 4;

	public int type;
	public byte[] data;

	public Recipe() {}

	public Recipe(int type, byte[] data) {
		this.type = type;
		this.data = data;
	}

	@Override
	public int length() {
		return Buffer.varintLength(type) + data.length;
	}

	@Override
	public byte[] encode() {
		this._buffer = new byte[this.length()];
		this.writeVarint(type);
		this.writeBytes(data);
		return this._buffer;
	}

	@Override
	public void decode(byte[] buffer) {
		this._buffer = buffer;
		type=this.readVarint();
		data=this.readBytes(this._buffer.length-this._index);
	}


}
