import pandas as pd
import matplotlib.pyplot as plt

# Cargar los datos del CSV
data = pd.read_csv("N_bueno.csv")

# Filtrar los datos por Método (CIM y Force)
cim_data = data[data['Method'] == 'CIM']
force_data = data[data['Method'] == 'Force']

# Graficar
plt.plot(cim_data['N'], cim_data['Time'], label='CIM', color='blue')
plt.plot(force_data['N'], force_data['Time'], label='Force', color='red')

# Configuración del gráfico
plt.xlabel('N')
plt.ylabel('Time')
plt.title('Time vs N')
plt.legend()

# Mostrar el gráfico
plt.show()
