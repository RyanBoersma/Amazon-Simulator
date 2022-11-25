local Json = require("json")

local Graph = {
	pathNodes = {},
	dockNodes = {},
	storageNodes = {},
	chargeNodes = {},

	connections = {},
}

local nodeID = 0
local function newPathNode(x, y, z)
	local id = nodeID
	
	local pathNode = {
		id = id,
		x = x,
		y = y,
		z = z*-1,
	}

	table.insert(Graph.pathNodes, pathNode)

	nodeID = id + 1

	return id
end

local function newStorageNode(x, y, z)
	local id = nodeID
	
	local storageNode = {
		id = id,
		x = x,
		y = y,
		z = z*-1,
	}

	table.insert(Graph.storageNodes, storageNode)

	nodeID = id + 1

	return id
end

local function newChargeNode(x, y, z)
	local id = nodeID
	
	local chargeNode = {
		id = id,
		x = x,
		y = y,
		z = z*-1,
	}

	table.insert(Graph.chargeNodes, chargeNode)

	nodeID = id + 1

	return id
end

local function newDockNode(x, y, z)
	local id = nodeID
	
	local dockNode = {
		id = id,
		x = x,
		y = y,
		z = z*-1,
	}

	table.insert(Graph.dockNodes, dockNode)

	nodeID = id + 1

	return id
end

local function connect(a, b)
	if (a == nil or b == nil) then
		error()
	end

	local connectionA = {
		from = a,
		to = b,
	}

	table.insert(Graph.connections, connectionA)
end

local grid = {}

-- Main grid
for x = 1, 13 do
	grid[x] = {}
	for z = 1, 8 do
		local offset = 0
		if (x > 6) then offset = offset + 0.125 end
		if (x > 7) then offset = offset + 0.125 end
	
		local id = newPathNode((x - 1) * 0.5 + offset, 0, (z - 1) * 0.5)
		grid[x][z] = id
	end
end

-- Storage 1
for x = 1, 5 do
	for z = 9, 13 do
		local offset = 0
		if (z > 9) then offset = offset + 0.125 end
		if (z > 10) then offset = offset + 0.125 end
		if (z > 11) then offset = offset + 0.125 end
		if (z > 12) then offset = offset + 0.125 end

		local id = newPathNode((x - 1) * 0.5, 0, (z - 1) * 0.5 + offset)
		grid[x][z] = id
	end
end

for x = 2, 4 do
	for z = 9, 12 do
		local offset = 0
		if (z > 9) then offset = offset + 0.125 end
		if (z > 10) then offset = offset + 0.125 end
		if (z > 11) then offset = offset + 0.125 end
		if (z > 12) then offset = offset + 0.125 end

		local id = newStorageNode((x - 1) * 0.5, 0, (z - 1) * 0.5 + offset + 0.3125)

		connect(id, grid[x][z])
		connect(grid[x][z], id)
		connect(id, grid[x][z+1])
		connect(grid[x][z+1], id)
	end
end

-- Storage 2
for x = 9, 13 do
	for z = 9, 13 do
		local xoffset = 0.25
		local offset = 0
		if (z > 9) then offset = offset + 0.125 end
		if (z > 10) then offset = offset + 0.125 end
		if (z > 11) then offset = offset + 0.125 end
		if (z > 12) then offset = offset + 0.125 end

		local id = newPathNode((x - 1) * 0.5 + xoffset, 0, (z - 1) * 0.5 + offset)
		grid[x][z] = id
	end
end

for x = 10, 12 do
	for z = 9, 12 do
		local xoffset = 0.25
		local offset = 0
		if (z > 9) then offset = offset + 0.125 end
		if (z > 10) then offset = offset + 0.125 end
		if (z > 11) then offset = offset + 0.125 end
		if (z > 12) then offset = offset + 0.125 end

		local id = newStorageNode((x - 1) * 0.5 + xoffset, 0, (z - 1) * 0.5 + offset + 0.3125)

		connect(id, grid[x][z])
		connect(grid[x][z], id)
		connect(id, grid[x][z+1])
		connect(grid[x][z+1], id)
	end
end

-- Storage 3
for x = 14, 18 do
	grid[x] = {}
	for z = 4, 8 do
		local xoffset = 0.25
		local offset = 0
		if (x > 14) then xoffset = xoffset + 0.125 end
		if (x > 15) then xoffset = xoffset + 0.125 end
		if (x > 16) then xoffset = xoffset + 0.125 end
		if (x > 17) then xoffset = xoffset + 0.125 end

		local id = newPathNode((x - 1) * 0.5 + xoffset, 0, (z - 1) * 0.5 + offset)
		grid[x][z] = id
	end
end

-- Storage 3
for x = 14, 17 do
	for z = 5, 7 do
		local xoffset = 0.25
		local offset = 0
		if (x > 14) then xoffset = xoffset + 0.125 end
		if (x > 15) then xoffset = xoffset + 0.125 end
		if (x > 16) then xoffset = xoffset + 0.125 end
		if (x > 17) then xoffset = xoffset + 0.125 end

		local id = newStorageNode((x - 1) * 0.5 + xoffset + 0.3125, 0, (z - 1) * 0.5 + offset)

		connect(id, grid[x][z])
		connect(grid[x][z], id)
		connect(id, grid[x+1][z])
		connect(grid[x+1][z], id)
	end
end

-- Main grid
for x = 1, 13 do
	for z = 1, 8 do
		local id = grid[x][z]

		if (x > 1) then
			connect(id, grid[x-1][z])
		end

		if (x < 13) then
			connect(id, grid[x+1][z])
		end

		if (z > 1) then
			connect(id, grid[x][z-1])
		end

		if (z < 8) then
			connect(id, grid[x][z+1])
		end
	end
end

-- Storage 1
for x = 1, 5 do
	for z = 9, 13 do
		local id = grid[x][z]

		if (x > 1) then
			connect(id, grid[x-1][z])
		end

		if (x < 5) then
			connect(id, grid[x+1][z])
		end

		if (x == 1 or x == 5) then
		 connect(id, grid[x][z-1])
		end
		if ((x == 1 or x == 5) and z < 13) then
			connect(id, grid[x][z+1])
		end
	end

	local a = grid[x][9]
	local b = grid[x][8]
	connect(a, b)
	connect(b, a)
end

-- Storage 2
for x = 9, 13 do
	for z = 9, 13 do
		local id = grid[x][z]

		if (x > 9) then
			connect(id, grid[x-1][z])
		end

		if (x < 13) then
			connect(id, grid[x+1][z])
		end

		if (x == 9 or x == 13) then
		 connect(id, grid[x][z-1])
		end
		if ((x == 9 or x == 13) and z < 13) then
			connect(id, grid[x][z+1])
		end
	end

	local a = grid[x][9]
	local b = grid[x][8]
	connect(a, b)
	connect(b, a)
end

-- Storage 2
for x = 14, 18 do
	for z = 4, 8 do
		local id = grid[x][z]

		if (z > 4) then
			connect(id, grid[x][z-1])
		end

		if (z < 8) then
			connect(id, grid[x][z+1])
		end

		if (z == 4 or z == 8) then
		 connect(id, grid[x-1][z])
		end
		if ((z == 4 or z == 8) and x < 18) then
			connect(id, grid[x+1][z])
		end
	end
end

for z = 4, 8 do
	local a = grid[13][z]
	local b = grid[14][z]
	connect(a, b)
	connect(b, a)
end

-- Charging
do
	local prev = grid[1][5]
	for x = 1, 5 do
		local p = newPathNode(x * -0.375, 0, 2)

		connect(p, prev)
		connect(prev, p)

		local t = newChargeNode(x * -0.375, 0, 2.375)
		local b = newChargeNode(x * -0.375, 0, 1.625)

		connect(p, t)
		connect(t, p)
		connect(p, b)
		connect(b, p)

		prev = p
	end
end

-- Dock
do
	local a = newDockNode(0.5, 0, -0.5)
	local b = newDockNode(2, 0, -0.5)
	local c = newDockNode(4.25, 0, -0.5)
	local d = newDockNode(5.75, 0, -0.5)

	connect(a, grid[2][1])
	connect(grid[2][1], a)

	connect(b, grid[5][1])
	connect(grid[5][1], b)

	connect(c, grid[9][1])
	connect(grid[9][1], c)

	connect(d, grid[12][1])
	connect(grid[12][1], d)
end

local result = Json.encode(Graph)
love.filesystem.remove("graph.json")
love.filesystem.write("graph.json", result)

local function toScreenCoords(x, y)
	local x = 175 + x * 60
	local y = 75 - (y * 60)

	return x, y
end


local lookup = {}

for _, node in ipairs(Graph.pathNodes) do
	lookup[node.id] = node
end

for _, node in ipairs(Graph.storageNodes) do
	lookup[node.id] = node
end

for _, node in ipairs(Graph.chargeNodes) do
	lookup[node.id] = node
end

for _, node in ipairs(Graph.dockNodes) do
	lookup[node.id] = node
end

local cn = {}
local realConnections = {}

for _, connection in ipairs(Graph.connections) do
	if (not cn[connection.from]) then
		cn[connection.from] = {}
	end

	cn[connection.from][connection.to] = true
	
	if (cn[connection.to] and cn[connection.to][connection.from]) then
		table.insert(realConnections, connection)
	end
end

function love.draw()
	love.graphics.setColor(1, 1, 1)
	for _, node in ipairs(Graph.pathNodes) do
		local x, y = toScreenCoords(node.x, node.z)

		love.graphics.circle("fill", x, y, 6)
	end
	
	love.graphics.setColor(1, 0, 0)
	for _, node in ipairs(Graph.storageNodes) do
		local x, y = toScreenCoords(node.x, node.z)

		love.graphics.circle("fill", x, y, 6)
	end

	love.graphics.setColor(0, 1, 0)
	for _, node in ipairs(Graph.chargeNodes) do
		local x, y = toScreenCoords(node.x, node.z)

		love.graphics.circle("fill", x, y, 6)
	end
	
	love.graphics.setColor(0, 0, 1)
	for _, node in ipairs(Graph.dockNodes) do
		local x, y = toScreenCoords(node.x, node.z)

		love.graphics.circle("fill", x, y, 6)
	end


	love.graphics.setColor(1, 1, 1)
	for _, connection in ipairs(realConnections) do
		local n1 = lookup[connection.from]
		local n2 = lookup[connection.to]

		local x1, y1 = toScreenCoords(n1.x, n1.z)
		local x2, y2 = toScreenCoords(n2.x, n2.z)
		love.graphics.line(x1, y1, x2, y2)
	end
end