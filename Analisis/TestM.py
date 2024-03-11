import pandas as pd
import matplotlib.pyplot as plt

# Cargar los datos del CSV
data = pd.read_csv("TimeMAnalysis.csv")

# Filtrar los datos por Método (CIM y Force)
cim_data = data[data['Method'] == 'CIM']
force_data = data[data['Method'] == 'Force']

# Graficar
plt.plot(cim_data['M'], cim_data['results'], label='CIM', color='blue')
plt.plot(force_data['M'], force_data['results'], label='Force', color='red')

# Configuración del gráfico
plt.xlabel('M')
plt.ylabel('results')
plt.title('results vs M')
plt.legend()

# Mostrar el gráfico
plt.show()

'''
Del grafico se ve que empieza a caer los resultados en
Method,M,Time,results
CIM,17,32,13706
=>  el M que tengo que usar es 16
Que justo 16 = 16/20 = 1.25 = radio de particula + radio de cercanía.
'''

# Graficar
plt.plot(cim_data['M'], cim_data['Time'], label='CIM', color='blue')
plt.plot(force_data['M'], force_data['Time'], label='Force', color='red')

# Configuración del gráfico
plt.xlabel('M')
plt.ylabel('Time')
plt.title('Time vs M')
plt.legend()

# Mostrar el gráfico
plt.show()

