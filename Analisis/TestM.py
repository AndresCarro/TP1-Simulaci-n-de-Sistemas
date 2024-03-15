import pandas as pd
import matplotlib.pyplot as plt

# Cargar los datos del CSV
data = pd.read_csv("../TP1/MAnalysis.csv")

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
CIM,13,204,116676
CIM,14,202,116588
=>  el M que tengo que usar es 13
Que justo 13 => 20/13 = 1.53 = radio de particula * 2 + radio de cercanía.

Tambien con otro intento por ejemplo con radio de 
  "radius": 0.75,
  "radiusNeighbour": 1.5,

Method,M,Time,results
CIM,6,2361,394756
CIM,7,2150,394584

=> el M que tengo que usar es 6
Que justo 6 => 20/6 = 3.33 = radio de particula * 2 + radio de cercanía.
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

