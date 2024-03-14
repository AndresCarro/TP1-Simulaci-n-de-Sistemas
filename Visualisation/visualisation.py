import matplotlib.pyplot as plt
import json

PARTICLES_COORDINATES_FILE = '../output.xyz'
CONFIG_FILE = '../TP1/input.json'


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


def draw_particles(x_coords, y_coords, main_particle, n, radius):
    colors = []
    for i in range(0, n):
        if i == main_particle:
            colors.append('r')
        else:
            colors.append('b')

    plt.scatter(x_coords, y_coords, c=colors, s=50, alpha=1)
    circle_radius = plt.Circle((x_coords[main_particle], y_coords[main_particle]), radius, color='black', fill=False)
    plt.gca().add_patch(circle_radius)
    plt.xlabel('X Position')
    plt.ylabel('Y Position')
    plt.title('2D Graphic of Particles')
    plt.grid(True)
    plt.show()


if __name__ == '__main__':
    x_positions, y_positions = read_coords_file(PARTICLES_COORDINATES_FILE)
    config = read_config_file(CONFIG_FILE)
    draw_particles(x_positions, y_positions, config['particle'], config['N'], config['radius'])


