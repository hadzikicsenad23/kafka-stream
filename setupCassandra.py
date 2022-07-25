from cassandra.cluster import Cluster

if __name__ == "__main__":
    cluster = Cluster(['127.0.0.1'],port=9042)
    session = cluster.connect()
    session.execute("CREATE KEYSPACE IF NOT EXISTS signal WITH REPLICATION = { 'class' : 'SimpleStrategy','replication_factor' : 1}")
    session.execute("USE signal")
    session.execute('CREATE TABLE IF NOT EXISTS signal.signal_sample(vehicle_id text, recorded_at timestamp, signal text, value double, PRIMARY KEY ((vehicle_id, signal), recorded_at) ) WITH CLUSTERING ORDER BY (recorded_at DESC)')