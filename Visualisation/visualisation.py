import matplotlib.pyplot as plt
import json

PARTICLES_COORDINATES_FILE = '../TP1/output.xyz'
CONFIG_FILE = '../TP1/input.json'
CIM_NEIGHBOURS_FILE = '../TP1/cim_output.json'


def read_coords_file(file_path):
    with open(file_path, 'r') as file:
        # Read the number of atoms
        num_atoms = int(file.readline())

        # Skip the comment line
        file.readline()

        # Initialize lists to store atom symbols and coordinates
        x_positions = []
        y_positions = []

        # Read atom symbols and coordinates
        for line in file:
            parts = line.split()
            x_positions.append(float(parts[0]))
            y_positions.append(float(parts[1]))

    return x_positions, y_positions


def read_config_file(file_path):
    with open(file_path, 'r') as file:
        config_data = json.load(file)
    return config_data


def draw_particles(x_coords, y_coords, main_particle, n, radius, longitude, neighbours, general_radius):
    plt.figure(figsize=(8,8))
    ax = plt.gca()
    circle_radius = plt.Circle((x_coords[main_particle], y_coords[main_particle]), radius + general_radius, color='black', fill=False)
    ax.add_patch(circle_radius)

    if x_coords[main_particle] - (radius + general_radius) < 0:
        circle_radius = plt.Circle((x_coords[main_particle] + longitude, y_coords[main_particle]), radius + general_radius, color='black', fill=False)
        ax.add_patch(circle_radius)
    if x_coords[main_particle] + (radius + general_radius) > longitude:
        circle_radius = plt.Circle((0 - x_coords[main_particle], y_coords[main_particle]), radius + general_radius, color='black', fill=False)
        ax.add_patch(circle_radius)
    if y_coords[main_particle] - (radius + general_radius) < 0:
        circle_radius = plt.Circle((x_coords[main_particle], y_coords[main_particle] + longitude), radius + general_radius, color='black', fill=False)
        ax.add_patch(circle_radius)
    if y_coords[main_particle] + (radius + general_radius) > longitude:
        circle_radius = plt.Circle((y_coords[main_particle], 0 - y_coords[main_particle]), radius + general_radius, color='black', fill=False)
        ax.add_patch(circle_radius)
    if x_coords[main_particle] - (radius + general_radius) < 0 and y_coords[main_particle] - (radius + general_radius) < 0:
        circle_radius = plt.Circle((x_coords[main_particle] + longitude, y_coords[main_particle] + longitude), radius + general_radius, color='black', fill=False)
        ax.add_patch(circle_radius)
    if x_coords[main_particle] + (radius + general_radius) > longitude and y_coords[main_particle] + (radius + general_radius) > longitude:
        circle_radius = plt.Circle((0 - x_coords[main_particle], 0 - y_coords[main_particle]), radius + general_radius, color='black', fill=False)
        ax.add_patch(circle_radius)


    for id in neighbours:
        if id == str(main_particle):
            circle = plt.Circle((x_coords[int(id)], y_coords[int(id)]), radius=general_radius, color='green', fill=True)
            ax.add_patch(circle)
        elif int(id) in neighbours[str(main_particle)]:
            circle = plt.Circle((x_coords[int(id)], y_coords[int(id)]), radius=general_radius, color='red', fill=True)
            ax.add_patch(circle)
        else:
            circle = plt.Circle((x_coords[int(id)], y_coords[int(id)]), radius=general_radius, color='blue', fill=True)
            ax.add_patch(circle)
    plt.xlim(0, longitude)
    plt.ylim(0, longitude)
    ax.set_aspect('equal', adjustable='box')
    plt.xlabel('X Position')
    plt.ylabel('Y Position')
    plt.title('2D Graphic of Particles')
    plt.grid(True)
    plt.show()


def read_cim_neighbours(file_path):
    with open(file_path, 'r') as file:
        particle_neighbours = json.load(file)
    return particle_neighbours


if __name__ == '__main__':
    x_positions, y_positions = read_coords_file(PARTICLES_COORDINATES_FILE)
    config = read_config_file(CONFIG_FILE)
    neighbours = read_cim_neighbours(CIM_NEIGHBOURS_FILE)
    draw_particles(x_positions, y_positions, config['particle'], config['N'], config['radiusNeighbour'], config['L'], neighbours['particlesNeighbours'], config['radius'])


